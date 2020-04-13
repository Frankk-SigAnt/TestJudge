package xyz.macromogic.testjudge.util;

import xyz.macromogic.testjudge.check.AnswerChecker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;

import static xyz.macromogic.testjudge.TestJudge.STDIN;
import static xyz.macromogic.testjudge.TestJudge.STDOUT;

public class RuntimeJudge {
    private Method mainMethod;
    private TestCase testCase;
    private AnswerChecker checker;
    private long timeLimit;

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public RuntimeJudge(Method mainMethod, TestCase testCase, AnswerChecker checker, long timeLimit) {
        this.mainMethod = mainMethod;
        this.testCase = testCase;
        this.checker = checker;
        this.timeLimit = timeLimit;
    }

    public JudgeResult runAndJudge() {
        JudgeResult result = new JudgeResult();
        System.setIn(new ByteArrayInputStream(testCase.getInput().getBytes()));
        ByteArrayOutputStream outputBuf = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputBuf);
        System.setOut(testOut);

        CountDownLatch latch = new CountDownLatch(1);
        Thread judgeThread = new Thread(() -> {
            try {
                mainMethod.invoke(null, (Object) testCase.getArgs());
            } catch (IllegalAccessException e) {
                result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof ThreadDeath) {
                    result.update(JudgeResult.Verdict.RUNTIME_ERROR, "");
                } else {
                    result.update(JudgeResult.Verdict.RUNTIME_ERROR, e.getTargetException().toString());
                }
            }
            latch.countDown();
        });
        judgeThread.start();

        try {
            if (latch.await(timeLimit, TimeUnit.SECONDS)) {
                if (result.isRuntimeSuccess()) {
                    result.update(checker.check(testCase.getInput(), outputBuf.toString(), testCase.getReferenceOutput()));
                }
            } else {
                result.update(JudgeResult.Verdict.TIME_LIMIT_EXCEED, "");
                judgeThread.stop();
                synchronized (Thread.currentThread()){
                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException e) {
            result.update(JudgeResult.Verdict.RUNTIME_ERROR, "Judge thread interrupted\n");
        }

        System.setIn(STDIN);
        System.setOut(STDOUT);
        return result;
    }
}

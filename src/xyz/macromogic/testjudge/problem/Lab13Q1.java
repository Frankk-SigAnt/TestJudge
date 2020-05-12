package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab13Q1 extends Problem {
    @Override
    protected String run(String studentPath) {
        FileLoader.clear();
        try {
            FileLoader.load(studentPath + "TestInputMismatchException.java");
            result.update(compiler.compile(baseDirPath + "TestInputMismatchException.java"));
            if (result.isAccepted()) {
                Class<?> testClass = compiler.loadClass("TestInputMismatchException");
                if (testClass == null) {
                    result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class TestInputMismatchException\n");
                    throw new TestException();
                }
                result.update(new RuntimeJudge(testClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1).runAndJudge());
            }
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load TestInputMismatchException.java\n");
        } catch (NoSuchMethodException e) {
            result.update(JudgeResult.Verdict.RUNTIME_ERROR, "Cannot load main method\n");
        } catch (TestException ignored) {
        }
        return "Q1 (File has been renamed to TestInputMismatchException.java):\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "a 1\n" +
                    "1 a\n" +
                    "400 11",
            ""
    );
}

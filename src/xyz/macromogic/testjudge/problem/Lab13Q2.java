package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab13Q2 extends Problem {
    @Override
    protected String run(String studentPath) {
        FileLoader.clear();
        try {
            FileLoader.load(studentPath + "CommonExceptionDemo.java");
            result.update(compiler.compile(baseDirPath + "CommonExceptionDemo.java"));
            if (result.isAccepted()) {
                Class<?> testClass = compiler.loadClass("CommonExceptionDemo");
                if (testClass == null) {
                    result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class CommonExceptionDemo\n");
                    throw new TestException();
                }
                for (TestCase testCase : testCases) {
                    result.update(new RuntimeJudge(testClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1).runAndJudge());
                    if (!result.isAccepted()) {
                        throw new TestException();
                    }
                }
            }
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load CommonExceptionDemo.java\n");
        } catch (NoSuchMethodException e) {
            result.update(JudgeResult.Verdict.RUNTIME_ERROR, "Cannot load main method\n");
        } catch (TestException ignored) {
        }
        return "Q2\n:" + result.toString();
    }

    private TestCase[] testCases = {
            new TestCase(
                    new String[]{},
                    "2",
                    ""
            ),
            new TestCase(
                    new String[]{},
                    "9",
                    ""
            ),
            new TestCase(
                    new String[]{},
                    "r",
                    ""
            ),
    };
}

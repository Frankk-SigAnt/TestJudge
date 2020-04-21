package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab10Q2 extends Problem {
    @Override
    protected String run(String studentPath) {
        try {
            FileLoader.load(studentPath + "TestAnagramString.java");
            result.update(compiler.compile(baseDirPath + "TestAnagramString.java"));
            Class<?> mainClass = compiler.loadClass("TestAnagramString");
            if (mainClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class TestAnagramString\n");
                throw new TestException();
            }
            RuntimeJudge runtimeJudge = new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1);
            result.update(runtimeJudge.runAndJudge());
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load TestAnagramString.java\n");
        } catch (NoSuchMethodException e) {
            result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
        } catch (TestException ignored) {
        }
        return "Q2:\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "TestJudge sucks\n" +
                    "Test#Judge...sucks!\n" +
                    "y\n" +
                    "apple\n" +
                    "polar\n" +
                    "n\n",
            ""
    );
}

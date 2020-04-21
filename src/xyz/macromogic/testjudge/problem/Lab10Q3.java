package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.reflect.test.Lab10Q3Test;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab10Q3 extends Problem {
    @Override
    protected String run(String studentPath) {
        try {
            try {
                FileLoader.load(studentPath + "ClassRoom.java");
            } catch (IOException e) {
                result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load ClassRoom.java\n");
                throw new TestException();
            }
            try {
                FileLoader.load(studentPath + "TestClassRoom.java");
            } catch (IOException e) {
                result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load TestClassRoom.java\n");
                throw new TestException();
            }
            result.update(compiler.compile(baseDirPath + "ClassRoom.java", baseDirPath + "TestClassRoom.java"));
            Class<?> testClass = compiler.loadClass("ClassRoom");
            if (testClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class ClassRoom\n");
                throw new TestException();
            }
            ReflectJudge test = new Lab10Q3Test();
            result.update(test.judge(testClass));
            if (!result.isAccepted()) {
                throw new TestException();
            }
            Class<?> mainClass = compiler.loadClass("TestClassRoom");
            if (mainClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class TestClassRoom\n");
                throw new TestException();
            }
            RuntimeJudge runtimeJudge = new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1);
            result.update(runtimeJudge.runAndJudge());
        } catch (NoSuchMethodException e) {
            result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
        } catch (TestException ignored) {
        }
        return "Q3:\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "3\n" +
                    "2 5 100",
            ""
    );
}


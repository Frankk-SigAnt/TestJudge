package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.reflect.test.Lab9Q2Test;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab9Q2 extends Problem {
    @Override
    protected String run(String studentPath) {
        try {
            try {
                FileLoader.load(studentPath + "ClassRoom.java");
            } catch (IOException ignored) {
            }
            FileLoader.load("tmp/ClassRoomRuntimeTest.java");
            result.update(compiler.compile(baseDirPath + "ClassRoom.java"));
            Class<?> triangleClass = compiler.loadClass("ClassRoom");
            if (triangleClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class ClassRoom\n");
                throw new TestException();
            }
            ReflectJudge test = new Lab9Q2Test();
            result.update(test.judge(triangleClass));
            if (!result.isAccepted()) {
                throw new TestException();
            }
            result.update(compiler.compile(baseDirPath + "ClassRoom.java", baseDirPath + "ClassRoomRuntimeTest.java"));
            Class<?> mainClass = compiler.loadClass("ClassRoomRuntimeTest");
            try {
                RuntimeJudge runtimeJudge = new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1);
                result.update(runtimeJudge.runAndJudge());
            } catch (NoSuchMethodException e) {
                result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
            }
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "");
        } catch (TestException ignored) {
        }
        return "Q2:\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "",
            ""
    );
}

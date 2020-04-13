package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.TokenChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.reflect.test.Lab9Q1Test;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab9Q1 extends Problem {
    @Override
    protected String run(String studentPath) {
        try {
            try {
                FileLoader.load(studentPath + "Triangle.java");
            } catch (IOException ignored) {
            }
            FileLoader.load("tmp/TriangleRuntimeTest.java");
            result.update(compiler.compile(baseDirPath + "Triangle.java"));
            Class<?> triangleClass = compiler.loadClass("Triangle");
            if (triangleClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class Triangle\n");
                throw new TestException();
            }
            ReflectJudge test = new Lab9Q1Test();
            result.update(test.judge(triangleClass));
            if (!result.isAccepted()) {
                throw new TestException();
            }
            result.update(compiler.compile(baseDirPath + "Triangle.java", baseDirPath + "TriangleRuntimeTest.java"));
            Class<?> mainClass = compiler.loadClass("TriangleRuntimeTest");
            try {
                RuntimeJudge runtimeJudge = new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new TokenChecker(), 1);
                result.update(runtimeJudge.runAndJudge());
            } catch (NoSuchMethodException e) {
                result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
            }
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "");
        } catch (TestException ignored) {
        }
        return "Q1:\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "",
            "Test default triangle:\n" +
                    "a=3, b=4, c=5, perimeter=12, area=6.00, valid=true\n" +
                    "Test modified triangle:\n" +
                    "a=4, b=3, c=2, perimeter=9, area=2.90, valid=true\n" +
                    "a=4, b=3, c=1, valid=false"
    );
}

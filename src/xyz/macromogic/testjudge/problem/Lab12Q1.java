package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.lazy.LazyFileChecker;
import xyz.macromogic.testjudge.reflect.test.Lab12Q1Test;
import xyz.macromogic.testjudge.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab12Q1 extends Problem {
    @Override
    protected String run(String studentPath) {
        FileLoader.clear();
        String[] fileNames = {"ClassRoom", "TestAvailableClassroom"};
        try {
            for (int i = 0; i < 2; i++) {
                FileLoader.load(studentPath + fileNames[i] + ".java");
            }
            FileLoader.load("tmp/classroom.txt");
        } catch (IOException ignored) {
        }
        try {
            String[] loadedFileNames = Arrays.stream(
                    Objects.requireNonNull(
                            new File(baseDirPath).list((f, name) -> name.endsWith(".java"))))
                    .map(e -> baseDirPath + e)
                    .toArray(String[]::new);
            result.update(compiler.compile(loadedFileNames));
            Class<?> testClass = compiler.loadClass("ClassRoom");
            if (testClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class ClassRoom\n");
            }
            result.update(new Lab12Q1Test().judge(testClass));
            if (!result.isAccepted()) {
                throw new TestException();
            }
            Class<?> mainClass = compiler.loadClass("TestAvailableClassRoom");
            if (mainClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class TestAvailableClassRoom\n");
                throw new TestException();
            }
            result.update(new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new LazyFileChecker(), 1).runAndJudge());
        } catch (NullPointerException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load files.\n");
        } catch (NoSuchMethodException e) {
            result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
        } catch (TestException ignored) {
        }
        return "Q1:\n" + result.toString();
    }

    private TestCase testCase = new TestCase(
            new String[]{},
            "classroom.txt\n" +
                    "7 1 50",
            ""
    );
}

package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.reflect.test.Lab12CircleTest;
import xyz.macromogic.testjudge.reflect.test.Lab12GeometricObjectTest;
import xyz.macromogic.testjudge.reflect.test.Lab12RectangleTest;
import xyz.macromogic.testjudge.util.FileLoader;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab12Q2 extends Problem {
    @Override
    protected String run(String studentPath) {
        FileLoader.clear();
        String[] fileNames = {"GeometricObject", "Circle", "Rectangle"};
        ReflectJudge[] judges = {new Lab12GeometricObjectTest(), new Lab12CircleTest(), new Lab12RectangleTest()};
        for (int i = 0; i < 3; i++) {
            try {
                FileLoader.load(studentPath + fileNames[i] + ".java");
            } catch (IOException e) {
                result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Failed to load file " + fileNames[i] + ".java\n");
            }
            try {
                String[] loadedFileNames = Arrays.stream(
                        Objects.requireNonNull(
                                new File(baseDirPath).list((f, name) -> name.endsWith(".java"))))
                        .map(e -> baseDirPath + e)
                        .toArray(String[]::new);
                result.update(compiler.compile(loadedFileNames));
                Class<?> testClass = compiler.loadClass(fileNames[i]);
                if (testClass == null) {
                    result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class " + fileNames[i] + "\n");
                    throw new TestException();
                }
                result.update(judges[i].judge(testClass));
            } catch (NullPointerException e) {
                result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
            } catch (TestException ignored) {
            }
        }
        return "Q2:\n" + result.toString();
    }
}

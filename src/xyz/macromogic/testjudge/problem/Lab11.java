package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.reflect.test.Lab11PersonTest;
import xyz.macromogic.testjudge.reflect.test.Lab11StudentAssistantTest;
import xyz.macromogic.testjudge.reflect.test.Lab11StudentTest;
import xyz.macromogic.testjudge.util.FileLoader;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.io.IOException;
import java.util.stream.IntStream;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab11 extends Problem {
    @Override
    protected String run(String studentPath) {
        StringBuilder resultBuffer = new StringBuilder();
        String[] fileNames = {"Person", "Student", "StudentAssistant"};
        ReflectJudge[] judges = {new Lab11PersonTest(), new Lab11StudentTest(), new Lab11StudentAssistantTest()};
        for (int i = 0; i < 3; i++) {
            JudgeResult result = new JudgeResult();
            try {
                try {
                    FileLoader.load(studentPath + fileNames[i] + ".java");
                } catch (IOException e) {
                    result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load " + fileNames[i] + ".java\n");
                    throw new TestException();
                }
//                result.update(compiler.compile(baseDirPath + fileNames[i] +  ".java"));
                result.update(compiler.compile(IntStream.range(0, i+1)
                        .mapToObj(j -> baseDirPath + fileNames[j] + ".java")
                        .toArray(String[]::new)));
                Class<?> testClass = compiler.loadClass(fileNames[i]);
                if (testClass == null) {
                    result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class " + fileNames[i] +"\n");
                    throw new TestException();
                }
                result.update(judges[i].judge(testClass));
            } catch (TestException ignored) {
            } finally {
                resultBuffer.append(String.format("Test %s:\n%s\n", fileNames[i], result.toString()));
                this.result.update(result);
            }
        }
        return resultBuffer.toString();
    }
}

package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.check.PromptChecker;
import xyz.macromogic.testjudge.util.*;

import java.io.IOException;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class Lab10Q1 extends Problem {
    @Override
    protected String run(String studentPath) {
        try {
            FileLoader.load(studentPath + "StringProcess.java");
            result.update(compiler.compile(baseDirPath + "StringProcess.java"));
            Class<?> mainClass = compiler.loadClass("StringProcess");
            if (mainClass == null) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, "Failed to load class StringProcess\n");
                throw new TestException();
            }
            try {
                RuntimeJudge runtimeJudge = new RuntimeJudge(mainClass.getMethod("main", String[].class), testCase, new PromptChecker(), 1);
                result.update(runtimeJudge.runAndJudge());
            } catch (NoSuchMethodException e) {
                result.update(JudgeResult.Verdict.UNEXPECTED_ERROR, "");
            }
        } catch (IOException e) {
            result.update(JudgeResult.Verdict.FILE_NOT_FOUND, "Cannot load StringProcess.java\n");
        } catch (TestException ignored) {
        }
        return "Q1:\n" + result.toString();
    }

    private TestCase testCase = new TestCase (
            new String[]{},
            "roses are red,violets are blue.             unexpected '{' on line 32",
            ""
    );
}

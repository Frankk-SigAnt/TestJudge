package xyz.macromogic.testjudge.check.lazy;

import xyz.macromogic.testjudge.check.AnswerChecker;
import xyz.macromogic.testjudge.util.JudgeResult;

import java.io.File;

public class LazyFileChecker implements AnswerChecker {
    @Override
    public JudgeResult check(String input, String output, String reference) {
        JudgeResult result = new JudgeResult();
        if (!new File("run/availableclassroom.txt").exists()) {
            result.update(JudgeResult.Verdict.RUNTIME_ERROR, "Output file not found.\n");
        }
        return result;
    }
}

package xyz.macromogic.testjudge.check;

import xyz.macromogic.testjudge.util.JudgeResult;

import java.util.Arrays;

public class TokenChecker implements AnswerChecker {
    @Override
    public JudgeResult check(String input, String output, String reference) {
        JudgeResult result = new JudgeResult();
        String[] outputTokens = output.split("\\s+");
        String[] referenceTokens = reference.split("\\s+");
        if (!Arrays.equals(outputTokens, referenceTokens)) {
            result.update(JudgeResult.Verdict.WRONG_ANSWER, String.format("Input:\n%s\nExpected:\n%s\nActual:\n%s\n", input, reference, output));
        }
        return result;
    }
}

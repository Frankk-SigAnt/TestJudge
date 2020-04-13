package xyz.macromogic.testjudge.check;

import xyz.macromogic.testjudge.util.JudgeResult;

import java.util.Scanner;

import static xyz.macromogic.testjudge.TestJudge.STDIN;
import static xyz.macromogic.testjudge.TestJudge.STDOUT;

public class Lab9Q2Checker implements AnswerChecker {
    private static Scanner stdin = new Scanner(STDIN);
    @Override
    public JudgeResult check(String input, String output, String reference) {
        JudgeResult result = new JudgeResult();
        // Check manually
        STDOUT.println(output);
        int res = stdin.nextInt();
        if (res != 0) {
            result.update(JudgeResult.Verdict.WRONG_ANSWER, output + "\n");
        }
        return result;
    }
}

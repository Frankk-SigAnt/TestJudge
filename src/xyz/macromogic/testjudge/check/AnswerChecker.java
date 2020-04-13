package xyz.macromogic.testjudge.check;

import xyz.macromogic.testjudge.util.JudgeResult;

public interface AnswerChecker {
    JudgeResult check(String input, String output, String reference);
}

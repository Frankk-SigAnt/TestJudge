package xyz.macromogic.testjudge.util;

public class JudgeResult {
    public enum Verdict {
        NO_TEST("Untested", -1),
        ACCEPT("Accepted", 0),
        WRONG_ANSWER("Wrong Answer", 1),
        RUNTIME_ERROR("Runtime Error", 2),
        TIME_LIMIT_EXCEED("Time Limit Exceeded", 3),
        REFLECTION_FAIL("Reflection Test Failed", 4),
        COMPILE_ERROR("Compile Error", 5),
        FILE_NOT_FOUND("File Not Found", 6),
        UNEXPECTED_ERROR("Unexpected Error", Integer.MAX_VALUE);

        private String name;
        private int priority;

        Verdict(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Verdict verdict;
    private StringBuilder message;

    public JudgeResult() {
        verdict = Verdict.ACCEPT;
        message = new StringBuilder();
    }

    public boolean isAccepted() {
        return verdict == Verdict.ACCEPT;
    }

    public boolean isRuntimeSuccess() {
        return verdict.getPriority() < Verdict.RUNTIME_ERROR.getPriority();
    }

    public void update(JudgeResult newResult) {
        update(newResult.verdict, newResult.message.toString());
    }

    public void update(Verdict verdict, String message) {
        if (this.verdict.getPriority() < verdict.getPriority()) {
            this.verdict = verdict;
        }
        this.message.append(message);
    }

    public void rewriteIfPrior(Verdict verdict, String message) {
        if (this.verdict.getPriority() < verdict.getPriority()) {
            this.verdict = verdict;
            this.message = new StringBuilder(message);
        }
    }

    @Override
    public String toString() {
        return String.format("Verdict: %s\nMessage:\n%s", verdict.toString(), message).trim();
    }
}

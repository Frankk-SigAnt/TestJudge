package xyz.macromogic.testjudge.util;

public class TestCase {
    private String[] args;
    private String input;
    private String referenceOutput;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getReferenceOutput() {
        return referenceOutput;
    }

    public void setReferenceOutput(String referenceOutput) {
        this.referenceOutput = referenceOutput;
    }

    public TestCase(String[] args, String input, String referenceOutput) {
        this.args = args;
        this.input = input;
        this.referenceOutput = referenceOutput;
    }
}

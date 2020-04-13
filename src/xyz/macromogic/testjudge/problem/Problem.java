package xyz.macromogic.testjudge.problem;

import xyz.macromogic.testjudge.util.FileCompiler;
import xyz.macromogic.testjudge.util.FileLoader;
import xyz.macromogic.testjudge.util.JudgeResult;

public abstract class Problem {
    protected FileCompiler compiler = new FileCompiler();
    protected JudgeResult result = new JudgeResult();

    protected void init() {
        FileLoader.clear();
    }

    public final String judge(String studentPath) {
        init();
        return run(studentPath);
    }

    public boolean isAccepted() {
        return result.isAccepted();
    }

    protected abstract String run(String studentPath);
}

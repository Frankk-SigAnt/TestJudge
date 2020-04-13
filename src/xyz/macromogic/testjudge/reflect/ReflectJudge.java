package xyz.macromogic.testjudge.reflect;

import xyz.macromogic.testjudge.util.JudgeResult;

public interface ReflectJudge {
    JudgeResult judge(Class<?> cls);
}

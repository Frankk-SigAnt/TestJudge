package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.lang.reflect.Constructor;

public class Lab11PersonTest implements ReflectJudge {
    @Override
    public JudgeResult judge(Class<?> cls) {
        JudgeResult result = new JudgeResult();
        try {
            checkAttributes(cls);
        } catch (TestException e) {
            result.update(JudgeResult.Verdict.REFLECTION_FAIL, e.getMessage());
        }
        return result;
    }

    private void checkAttributes(Class<?> cls) throws TestException {
        try {
            cls.getConstructor();
            cls.getConstructor(String.class, int.class);
        } catch (NoSuchMethodException e) {
            throw new TestException("Constructor missing: " + e.getMessage() + "\n");
        }
        FieldChecker.Entity[] fieldEntities = {
                new FieldChecker.Entity(true, false, String.class, "name"),
                new FieldChecker.Entity(true, false, int.class, "age")
        };
        try {
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
        } catch (NoSuchFieldException e) {
            throw new TestException("Field missing: " + e.getMessage() + "\n");
        }
        MethodChecker.Entity[] methodEntities = {
                new MethodChecker.Entity(false, false, String.class, "getName"),
                new MethodChecker.Entity(false, false, int.class, "getAge"),
                new MethodChecker.Entity(false, false, void.class, "setName", String.class),
                new MethodChecker.Entity(false, false, void.class, "setAge", int.class),
                new MethodChecker.Entity(false, false, String.class, "toString"),
                new MethodChecker.Entity(false, false, String.class, "enjoy"),
                new MethodChecker.Entity(false, false, String.class, "enjoy", String.class)
        };
        try {
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException e) {
            throw new TestException("Method missing: " + e.getMessage() + "\n");
        }
    }
}

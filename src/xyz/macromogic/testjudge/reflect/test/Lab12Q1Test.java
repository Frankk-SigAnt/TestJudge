package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.lang.reflect.Constructor;

public class Lab12Q1Test implements ReflectJudge {
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
            cls.getConstructor(int.class, int.class, int[][].class);
        } catch (NoSuchMethodException e) {
            throw new TestException("Constructor missing: " + e.getMessage() + "\n");
        }
        try {
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, false, int.class, "id"),
                    new FieldChecker.Entity(true, false, int.class, "seats"),
                    new FieldChecker.Entity(true, false, int[][].class, "arrangement")
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
        } catch (NoSuchFieldException e) {
            throw new TestException("Field missing: " + e.getMessage() + "\n");
        }
        try {
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, false, String.class, "toString"),
                    new MethodChecker.Entity(false, false, false, boolean.class, "isValidForExam", int.class, int.class, int.class)
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException e) {
            throw new TestException("Method missing: " + e.getMessage() + "\n");
        }
    }
}

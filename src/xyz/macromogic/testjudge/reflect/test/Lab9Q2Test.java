package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.util.TestException;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Lab9Q2Test implements ReflectJudge {
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
            Constructor<?> constructor = cls.getConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, false, int.class, "id"),
                    new FieldChecker.Entity(true, false, int.class, "seatsNumber"),
                    new FieldChecker.Entity(true, false, int[][].class, "arrangement"),
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, instance, entity);
            }
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, void.class, "printValidForExam", int.class, int.class, int.class),
                    new MethodChecker.Entity(false, false, void.class, "printClassRoom"),
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new TestException("Attribute (constructor/field/method) missing.\n");
        }
    }
}

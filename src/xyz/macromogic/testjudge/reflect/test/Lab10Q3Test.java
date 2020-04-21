package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Lab10Q3Test implements ReflectJudge {
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
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, true, int.class, "cnt"),
                    new FieldChecker.Entity(true, false, int.class, "id"),
                    new FieldChecker.Entity(true, false, int.class, "seatsNumber")
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, int.class, "getId"),
                    new MethodChecker.Entity(false, false, int.class, "getSeatsNumber"),
                    new MethodChecker.Entity(false, false, int[][].class, "getArrangement"),
                    new MethodChecker.Entity(false, false, String.class, "toString")
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new TestException("Attribute (constructor/field/method) missing in class ClassRoom, or typo exists.\n");
        }
    }
}

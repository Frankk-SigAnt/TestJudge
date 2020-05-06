package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.util.TestException;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Lab9Q1Test implements ReflectJudge {
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
            Constructor<?> constructor = cls.getConstructor(int.class, int.class, int.class);
            constructor.setAccessible(true);
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, false, int.class, "a"),
                    new FieldChecker.Entity(true, false, int.class, "b"),
                    new FieldChecker.Entity(true, false, int.class, "c"),
                    new FieldChecker.Entity(true, false, int.class, "perimeter"),
                    new FieldChecker.Entity(true, false, double.class, "area"),
                    new FieldChecker.Entity(true, false, boolean.class, "valid")
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, false, int.class, "getA"),
                    new MethodChecker.Entity(false, false, false, int.class, "getB"),
                    new MethodChecker.Entity(false, false, false, int.class, "getC"),
                    new MethodChecker.Entity(false, false, false, void.class, "setA", int.class),
                    new MethodChecker.Entity(false, false, false, void.class, "setB", int.class),
                    new MethodChecker.Entity(false, false, false, void.class, "setC", int.class),
                    new MethodChecker.Entity(false, false, false, int.class, "getPerimeter"),
                    new MethodChecker.Entity(false, false, false, double.class, "getArea"),
                    new MethodChecker.Entity(false, false, false, boolean.class, "isValid"),
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new TestException("Attribute (constructor/field/method) missing.\n");
        }
    }
}

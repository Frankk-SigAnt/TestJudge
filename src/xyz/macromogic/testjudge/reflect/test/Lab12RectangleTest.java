package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

public class Lab12RectangleTest implements ReflectJudge {
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
            cls.getConstructor(double.class, double.class);
            cls.getConstructor(double.class, double.class, String.class, boolean.class);
        } catch (NoSuchMethodException e) {
            throw new TestException("Constructor missing: " + e.getMessage() + "\n");
        }
        try {
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, false, double.class, "width"),
                    new FieldChecker.Entity(true, false, double.class, "height"),
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
        } catch (NoSuchFieldException e) {
            throw new TestException("Field missing: " + e.getMessage() + "\n");
        }
        try {
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, false, double.class, "getWidth"),
                    new MethodChecker.Entity(false, false, false, void.class, "setWidth", double.class),
                    new MethodChecker.Entity(false, false, false, double.class, "getHeight"),
                    new MethodChecker.Entity(false, false, false, void.class, "setHeight", double.class),
                    new MethodChecker.Entity(false, false, false, double.class, "getArea"),
                    new MethodChecker.Entity(false, false, false, double.class, "getPerimeter"),
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException e) {
            throw new TestException("Method missing: " + e.getMessage() + "\n");
        }
    }
}

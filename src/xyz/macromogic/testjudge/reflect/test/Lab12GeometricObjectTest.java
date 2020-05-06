package xyz.macromogic.testjudge.reflect.test;

import xyz.macromogic.testjudge.reflect.FieldChecker;
import xyz.macromogic.testjudge.reflect.MethodChecker;
import xyz.macromogic.testjudge.reflect.ReflectJudge;
import xyz.macromogic.testjudge.util.JudgeResult;
import xyz.macromogic.testjudge.util.TestException;

import java.util.Date;

public class Lab12GeometricObjectTest implements ReflectJudge {
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
            cls.getDeclaredConstructor();
            cls.getDeclaredConstructor(String.class, boolean.class);
        } catch (NoSuchMethodException e) {
            throw new TestException("Constructor missing: " + e.getMessage() + "\n");
        }
        try {
            FieldChecker.Entity[] fieldEntities = {
                    new FieldChecker.Entity(true, false, String.class, "color"),
                    new FieldChecker.Entity(true, false, boolean.class, "filled"),
                    new FieldChecker.Entity(true, false, Date.class, "dateCreated"),
            };
            for (FieldChecker.Entity entity : fieldEntities) {
                FieldChecker.check(cls, entity);
            }
        } catch (NoSuchFieldException e) {
            throw new TestException("Field missing: " + e.getMessage() + "\n");
        }
        try {
            MethodChecker.Entity[] methodEntities = {
                    new MethodChecker.Entity(false, false, false, String.class, "getColor"),
                    new MethodChecker.Entity(false, false, false, void.class, "setColor", String.class),
                    new MethodChecker.Entity(false, false, false, boolean.class, "isFilled"),
                    new MethodChecker.Entity(false, false, false, void.class, "setFilled", boolean.class),
                    new MethodChecker.Entity(false, false, false, Date.class, "getDateCreated"),
                    new MethodChecker.Entity(false, false, false, String.class, "toString"),
                    new MethodChecker.Entity(false, false, true, double.class, "getArea"),
                    new MethodChecker.Entity(false, false, true, double.class, "getPerimeter"),
            };
            for (MethodChecker.Entity entity : methodEntities) {
                MethodChecker.check(cls, entity);
            }
        } catch (NoSuchMethodException e) {
            throw new TestException("Method missing: " + e.getMessage() + "\n");
        }
    }
}

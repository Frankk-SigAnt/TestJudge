package xyz.macromogic.testjudge.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldChecker {
    public static class Entity {
        private boolean isPrivate;
        private boolean isStatic;
        private Class<?> type;
        private String name;

        public boolean isPrivate() {
            return isPrivate;
        }

        public boolean isStatic() {
            return isStatic;
        }

        public Class<?> getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public Entity(boolean isPrivate, boolean isStatic, Class<?> type, String name) {
            this.isPrivate = isPrivate;
            this.isStatic = isStatic;
            this.type = type;
            this.name = name;
        }
    }

    public static void check(Class<?> cls, Entity entity) throws NoSuchFieldException {
        if (entity.isPrivate()) {
            Field field = cls.getDeclaredField(entity.getName());
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) != entity.isStatic()) {
                throw new NoSuchFieldException();
            }
            if (!field.getType().equals(entity.getType())) {
                throw new NoSuchFieldException();
            }
            field.setAccessible(false);
        } else {
            Field field = cls.getField(entity.getName());
            if (Modifier.isStatic(field.getModifiers()) != entity.isStatic()) {
                throw new NoSuchFieldException();
            }
            if (!field.getType().equals(entity.getType())) {
                throw new NoSuchFieldException();
            }
        }
    }
}

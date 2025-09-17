package ru.mirea.hidden.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTestUtils {
    public static Class<?> getClass(String packageName, String className) {
        try {
            return Class.forName("ru.mirea."+packageName + "." + className);
        } catch (ClassNotFoundException e) {
            throw new AssertionError("❌ Класс '" + className + "' не найден", e);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null && !superclass.getSimpleName().equals("Object")) {
                try {
                    Field field = superclass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException ex) {
                    throw new AssertionError("❌ Поле '" + fieldName + "' не найдено в классе " + clazz.getSimpleName() + " и в родительском "+ superclass.getSimpleName(), ex);
                }
            } else {
                throw new AssertionError("❌ Поле '" + fieldName + "' не найдено в классе " + clazz.getSimpleName());
            }
        }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new AssertionError("❌ Метод '" + methodName + "' не найден в классе " + clazz.getSimpleName(), e);
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new AssertionError("❌ Ошибка при вызове метода " + method.getName(), e);
        }
    }

    public static Object createInstance(Class<?> clazz, Object... args) {
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == args.length) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(args);
                }
            }
            throw new AssertionError("❌ Не найден подходящий конструктор для " + clazz.getSimpleName());
        } catch (Exception e) {
            throw new AssertionError("❌ Ошибка при создании экземпляра " + clazz.getSimpleName(), e);
        }
    }
}

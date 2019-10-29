package me.qninh.kiwi.util;

import java.lang.reflect.Method;

public class ReflectUtil {

    public static Method getMethod(Class<?> clazz, String name, Class<?>... types) {
        try {
            Method method = clazz.getMethod(name, types);

            if (method == null) {
                method = clazz.getDeclaredMethod(name, types);
                method.setAccessible(true);
            }

            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }  
}
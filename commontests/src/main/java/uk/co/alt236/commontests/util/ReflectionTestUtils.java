package uk.co.alt236.commontests.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by alexandros on 06/10/2014.
 */
public class ReflectionTestUtils {

    public String getStaticStringField(final Class clazz, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (String) f.get(null);
    }

    public <T> Constructor<T> getUnprotectedDefaultConstructor(final Class<T> clazz) throws NoSuchMethodException {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError("No default constructor for " + clazz.getName());
        }
    }

    public <T> boolean hasDefaultConstructor(final Class<T> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    public <T> T instantiateViaDefaultConstructor(final Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return getUnprotectedDefaultConstructor(clazz).newInstance(new Object[0]);
    }
}

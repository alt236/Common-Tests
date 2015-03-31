package uk.co.alt236.commontests.tests.serializable;

import android.test.AndroidTestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import uk.co.alt236.commontests.util.SerializationUtils;

/**
 * Created by alexandros on 06/10/2014.
 */
public class SerializableFieldTestCase extends AndroidTestCase {
    private final Class<?> clazz;

    public SerializableFieldTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        test(clazz, clazz);
    }

    private static String getErrorMessage(final Class<?> originalClass, final Class<?> currentClass, final Field field) {

        if (originalClass.getCanonicalName().equals(currentClass.getCanonicalName())) {
            return "Field '" + field.getName() + " (" + field.getType() + ")"
                    + "' of '" + currentClass.getName()
                    + "' is marked to be stored as Serializable"
                    + " in '" + originalClass.getName() + "' but does not implement Serializable!";
        } else {
            return "Field '" + field.getName() + " (" + field.getType() + ")"
                    + "' of '" + currentClass.getName()
                    + "' is **INDIRECTLY** marked to be stored as Serializable"
                    + " in '" + originalClass.getName() + "' but does not implement Serializable!";
        }
    }

    private static boolean isSkippable(final Field field) {
        // Transient fields will not get Serialized
        if (Modifier.isTransient(field.getModifiers())) {
            return true;
        }

        // Static fields will not get Serialized
        // The should be marked as transient implicitly anyway
        if (Modifier.isStatic(field.getModifiers())) {
            return true;
        }

        // Strings are immutable and Serializable no need to go into them
        if (String.class.equals(field.getType())) {
            return true;
        }

        // Primitives are not marked as Serializable but Java takes care of that
        if (field.getType().isPrimitive()) {
            return true;
        }

        return false;
    }

    private static void test(final Class<?> originalClass, final Class<?> clazz) {
        if (!SerializationUtils.isSerializable(clazz)) {
            throw new IllegalArgumentException("Class '" + clazz.getName() + "' is not Serializable!");
        }

        final Field[] fields = clazz.getDeclaredFields();

        for (final Field field : fields) {
            if (!isSkippable(field)) {
                if (!SerializationUtils.isSerializable(field.getType())) {
                    throw new IllegalArgumentException(getErrorMessage(originalClass, clazz, field));
                } else {
                    test(originalClass, field.getType());
                }
            }
        }
    }
}
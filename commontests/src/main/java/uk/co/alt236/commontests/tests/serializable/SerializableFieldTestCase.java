package uk.co.alt236.commontests.tests.serializable;

import android.test.AndroidTestCase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import uk.co.alt236.commontests.util.SerializationUtils;

/**
 * Created by alexandros on 06/10/2014.
 */
public class SerializableFieldTestCase extends AndroidTestCase {
    private final Class<?> clazz;
    private final Set<String> exceptions;

    public SerializableFieldTestCase(final Class clazz, final Set<String> exceptions) {
        setName("testReflectively");
        this.clazz = clazz;
        this.exceptions = exceptions;
    }

    private boolean isSkippable(final Field field) {
        if(field == null){
            return true;
        }

        if(exceptions.contains(field.getType().getName())){
            return true;
        }
        // Primitives are not marked as Serializable but Java takes care of that
        if (field.getType().isPrimitive()) {
            return true;
        }

        // Strings are immutable and Serializable no need to go into them
        if (String.class.equals(field.getType())) {
            return true;
        }

        // Transient fields will not get Serialized
        if (Modifier.isTransient(field.getModifiers())) {
            return true;
        }

        // Static fields will not get Serialized
        // The should be marked as transient implicitly anyway
        if (Modifier.isStatic(field.getModifiers())) {
            return true;
        }

        return false;
    }

    private void test(final Class<?> originalClass, final Class<?> clazz) {
        if (!SerializationUtils.isSerializable(clazz)) {
            fail("Class '" + clazz.getName() + "' is not Serializable!");
        }

        final Field[] fields = clazz.getDeclaredFields();

        for (final Field field : fields) {
            if (isSkippable(field)) {
               // Nothing to do here
            } else {
                if (SerializationUtils.isSerializable(field.getType())) {
                    test(originalClass, field.getType());
                } else {
                    fail(getErrorMessage(originalClass, clazz, field));
                }
            }
        }
    }

    public void testReflectively() throws Exception {
        test(clazz, clazz);
    }

    private static String getErrorMessage(final Class<?> originalClass, final Class<?> currentClass, final Field field) {

        if (originalClass.getCanonicalName().equals(currentClass.getCanonicalName())) {
            return "Field '" + getFieldInfo(field)
                    + "' of '" + currentClass.getName()
                    + "' is marked to be stored as Serializable"
                    + " in '" + originalClass.getName() + "' but does not implement Serializable!";
        } else {
            return "Field '" + getFieldInfo(field)
                    + "' of '" + currentClass.getName()
                    + "' is **INDIRECTLY** marked to be stored as Serializable"
                    + " in '" + originalClass.getName() + "' but does not implement Serializable!";
        }
    }

    private static String getFieldInfo(final Field field){
        return field.getName() + " (" + field.getType() + ")";
    }
}
package uk.co.alt236.commontests.tests.ormlite;

import android.test.AndroidTestCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import uk.co.alt236.commontests.util.SerializationUtils;

/**
 * Created by alexandros on 06/10/2014.
 */
public class OrmLiteSerializableTestCase extends AndroidTestCase {
    private final Class<?> clazz;

    public OrmLiteSerializableTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        final String clazzName = clazz.getName();
        testMarkedFieldsAreSerializable(clazzName, clazz);
    }

    private static void testMarkedFieldsAreSerializable(final String originalClassName, final Class<?> clazz) {
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            final Annotation[] annotations = field.getDeclaredAnnotations();

            if (annotations != null) {

                for (Annotation annotation : annotations) {

                    if (annotation instanceof DatabaseField) {

                        final DataType type = ((DatabaseField) annotation).dataType();
                        if (type == DataType.SERIALIZABLE) {

                            if (!SerializationUtils.isSerializable(field.getType())) {
                                final String message = "Field '" + field.getName() + " (" + field.getType() + ")"
                                        + "' of '" + clazz.getName()
                                        + "' is marked to be stored as Serializable"
                                        + " in '" + originalClassName + "' but does not implement Serializable!";

                                throw new IllegalArgumentException(message);
                            }
                        }
                    }
                }
            }
        }
    }
}

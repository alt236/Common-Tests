package uk.co.alt236.commontests.util;

import java.io.Serializable;

/**
 * Created by alexandros on 31/03/2015.
 */
public final class SerializationUtils {
    public static boolean isSerializable(Class<?> clazz) {
        final boolean res;

        if (clazz == null) {
            res = false;
        } else {
            if (Serializable.class.isAssignableFrom(clazz)) {
                res = true;
            } else {
                res = isSerializable(clazz.getSuperclass());
            }
        }

        return res;
    }
}

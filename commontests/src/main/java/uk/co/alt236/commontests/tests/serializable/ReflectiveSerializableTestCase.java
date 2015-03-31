package uk.co.alt236.commontests.tests.serializable;

import android.test.AndroidTestCase;

import com.cedarsoftware.util.DeepEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import uk.co.alt236.commontests.util.ReflectionTestUtils;

/**
 * Created by alexandros on 06/10/2014.
 */
public class ReflectiveSerializableTestCase extends AndroidTestCase {
    private final Class<?> clazz;

    public ReflectiveSerializableTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        final String clazzName = clazz.getName();
        final ReflectionTestUtils utils = new ReflectionTestUtils();

        final Serializable serializableIn =
                (Serializable) utils.getUnprotectedDefaultConstructor(clazz).newInstance(new Object[0]);
        final Serializable serializableOut;

        final byte[] byteArray = serialize(serializableIn);

        serializableOut = unSerialize(byteArray, clazz);

        //Check that objects are not same and test that objects are equal
        assertFalse("Serializable is the same: " + clazzName, serializableIn == serializableOut);
        assertTrue("Serializables aren't equal: " + clazzName, DeepEquals.deepEquals(serializableIn, serializableOut));
    }

    private static <T> byte[] serialize(T obj) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    private static <T> Serializable unSerialize(byte[] b, Class<T> cl) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(b);
        final ObjectInputStream ois = new ObjectInputStream(bais);
        final Object o = ois.readObject();
        return (Serializable) cl.cast(o);
    }
}

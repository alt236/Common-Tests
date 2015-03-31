package uk.co.alt236.commontests.tests.pojo;

import android.test.AndroidTestCase;

import uk.co.alt236.commontests.util.ReflectionTestUtils;

/**
 * Created by alexandros on 06/10/2014.
 */
public class ToStringTestCase extends AndroidTestCase {

    private final Class<?> clazz;

    public ToStringTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        final String clazzName = clazz.getName();
        final ReflectionTestUtils utils = new ReflectionTestUtils();

        final Object objectIn =
                utils.getUnprotectedDefaultConstructor(clazz).newInstance(new Object[0]);
        assertNotNull("toString() is null: " + clazzName, objectIn.toString());
    }
}

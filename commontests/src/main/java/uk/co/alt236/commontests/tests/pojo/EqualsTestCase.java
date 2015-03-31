package uk.co.alt236.commontests.tests.pojo;

import android.test.AndroidTestCase;

import uk.co.alt236.commontests.util.ReflectionTestUtils;


/**
 * Created by alexandros on 06/10/2014.
 */
public class EqualsTestCase extends AndroidTestCase {

    private final Class<?> clazz;

    public EqualsTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        final String clazzName = clazz.getName();
        final ReflectionTestUtils utils = new ReflectionTestUtils();

        final Object objectIn = utils.getUnprotectedDefaultConstructor(clazz).newInstance(new Object[0]);
        assertFalse("Equals() equals with null yielded true: " + clazzName, objectIn.equals(null));
        assertFalse("Equals() equals with random object yielded true: " + clazzName, objectIn.equals(new Object()));
        assertTrue("Equals() equals with same object yielded false: " + clazzName, objectIn.equals(objectIn));
    }
}

package uk.co.alt236.commontests.util.base;

import uk.co.alt236.commontests.util.ReflectionTestUtils;

/**
 * Created by alexandros on 03/10/2014.
 */
public abstract class AbstractReflectiveTestCase extends android.test.AndroidTestCase {
    private ReflectionTestUtils reflectionTestUtils;

    protected ReflectionTestUtils getReflectionTestUtils() {
        return reflectionTestUtils;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        reflectionTestUtils = new ReflectionTestUtils();
    }
}

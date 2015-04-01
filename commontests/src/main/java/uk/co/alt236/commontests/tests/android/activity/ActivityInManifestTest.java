package uk.co.alt236.commontests.tests.android.activity;

import java.util.Set;

import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCase;

/**
 * Created by alexandros on 03/10/2014.
 */
public class ActivityInManifestTest extends AbstractReflectiveTestCase {

    private final Class<?> clazz;
    private final Set<String> foundNames;

    protected ActivityInManifestTest(final Class clazz, final Set<String> foundNames) {
        setName("testReflectively");
        this.clazz = clazz;
        this.foundNames = foundNames;
    }

    public void testReflectively() throws Exception {
        if(!foundNames.contains(clazz.getName())){
            fail("Activity '" + clazz.getName() + "' is not in the Manifest!");
        }
    }
}

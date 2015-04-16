package uk.co.alt236.commontests.tests.android.activity;

import android.test.AndroidTestCase;

/**
 * Created by alexandros on 31/03/2015.
 */
public class ActivityInManifestButNotInCodeTest extends AndroidTestCase {

    private final String activityName;

    protected ActivityInManifestButNotInCodeTest(final String activityName) {
        setName("testReflectively");
        this.activityName = activityName;
    }

    public void testReflectively() throws Exception {
        try {
            Class.forName(activityName);
        } catch (final ClassNotFoundException e) {
            fail("Activity '" + activityName + "' is in the Manifest but there is no corresponding class!");
        }
    }
}

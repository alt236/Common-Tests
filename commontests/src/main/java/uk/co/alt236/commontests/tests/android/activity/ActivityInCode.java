package uk.co.alt236.commontests.tests.android.activity;

import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCase;

/**
 * Created by alexandros on 31/03/2015.
 */
public class ActivityInCode extends AbstractReflectiveTestCase {

    private final String activityName;

    public ActivityInCode(final String activityName) {
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

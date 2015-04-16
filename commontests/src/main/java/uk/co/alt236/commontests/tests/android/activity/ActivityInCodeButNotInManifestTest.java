package uk.co.alt236.commontests.tests.android.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.test.AndroidTestCase;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by alexandros on 03/10/2014.
 */
public class ActivityInCodeButNotInManifestTest extends AndroidTestCase {

    private final Collection<String> classNames;
    private final Collection<String> manifestActivities;

    protected ActivityInCodeButNotInManifestTest(final Collection<String> classNames, final Set<String> manifestActivities) {
        setName("testReflectively");
        this.classNames = classNames;
        this.manifestActivities = manifestActivities;
    }

    public void testReflectively() throws Exception {

        final List<String> missingActivities = new ArrayList<>();

        for(final String clazzName : classNames){
            final Class<?> clazz = Class.forName(clazzName);
            if (isApplicable(clazz)){
                final boolean inManifest = manifestActivities.contains(clazzName);

                if (!inManifest) {
                    missingActivities.add(clazzName);
                }
            }

        }

        if (!missingActivities.isEmpty()) {
            fail("These Activities are not registered in the Manifest: " + missingActivities.toString());
        }
    }

    @SuppressWarnings("deprecation")
    private static boolean isApplicable(Class<?> clazz) {
        final boolean res;

        if (clazz == null) {
            res = false;
        } else {
            if(Modifier.isAbstract(clazz.getModifiers())){
                return false;
            }

            if (Activity.class.isAssignableFrom(clazz)) {
                res = true;
            } else if (TabActivity.class.isAssignableFrom(clazz)) {
                res = true;
            } else {
                res = isApplicable(clazz.getSuperclass());
            }
        }

        return res;
    }
}

package uk.co.alt236.commontests.tests.android;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import dalvik.system.DexFile;
import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCase;

/**
 * Created by alexandros on 03/10/2014.
 */
public class ActivityTest extends AbstractReflectiveTestCase {

    public void testActivitiesExistInManifest() throws Exception {
        final String packageName = getContext().getApplicationContext().getPackageName();

        final Set<String> activityNames = getAllManifestActivities(getContext(), packageName);
        final List<String> missingActivities = new ArrayList<String>();

        final DexFile df = getReflectionTestUtils().getDexFile();
        for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
            final String clazzName = iter.nextElement();

            if (getReflectionTestUtils().isFromValidPackage(clazzName)) {
                final Class<?> clazz = Class.forName(clazzName);
                if (!Modifier.isAbstract(clazz.getModifiers())) {

                    if (isActivity(clazz)) {
                        final boolean inManifest = activityNames.contains(clazzName);

                        if (!inManifest) {
                            missingActivities.add(clazzName);
                        }
                    }
                }
            }
        }

        if (!missingActivities.isEmpty()) {
            fail("These Activities are not registered in the Manifest: " + missingActivities.toString());
        }
    }

    public void testManifestActivities() throws Exception {
        final String packageName = getContext().getApplicationContext().getPackageName();
        final Set<String> activityNames = getAllManifestActivities(getContext(), packageName);
        final List<String> missingActivities = new ArrayList<String>();

        for (final String activity : activityNames) {
            try {
                Class.forName(activity);
            } catch (final ClassNotFoundException e) {
                if (!"android.support.v7.widget.TestActivity".equals(activity)) {
                    missingActivities.add(activity);
                }
            }
        }

        if (!missingActivities.isEmpty()) {
            fail("These Activities do not exist: " + missingActivities.toString());
        }
    }

    private static Set<String> getAllManifestActivities(Context context, String packageName) throws PackageManager.NameNotFoundException {
        final Set<String> result = new TreeSet<String>();

        final ActivityInfo[] list = context.getPackageManager().getPackageInfo(
                packageName,
                PackageManager.GET_ACTIVITIES).activities;

        for (final ActivityInfo info : list) {
            result.add(info.name);
        }

        return result;
    }

    private static boolean isActivity(Class<?> clazz) {
        final boolean res;

        if (clazz == null) {
            res = false;
        } else {
            if (Activity.class.isAssignableFrom(clazz)) {
                res = true;
            } else if (TabActivity.class.isAssignableFrom(clazz)) {
                res = true;
            } else if (FragmentActivity.class.isAssignableFrom(clazz)) {
                res = true;
            } else {
                res = isActivity(clazz.getSuperclass());
            }
        }

        return res;
    }
}

package uk.co.alt236.commontests.tests.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import junit.framework.TestSuite;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 31/03/2015.
 */
public class ActivityTestBuilder extends AbstractReflectiveTestCaseBuilder {

    public ActivityTestBuilder(final ClassFilter classFilter){
        super(classFilter);
    }

    public TestSuite getTests(final Context context) {
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getApplicableClasses();
        final Set<String> manifestActivities = getAllManifestActivities(context, context.getPackageName());

        for (final Class<?> clazz : listOfClasses) {
            selectedTests.addTest(new ActivityInManifestTest(clazz, manifestActivities));
        }

        for(final String clazz : manifestActivities){
            selectedTests.addTest(new ActivityInCode(clazz));
        }

        return selectedTests;
    }

    private static Set<String> getAllManifestActivities(Context context, String packageName) {
        final Set<String> result = new TreeSet<String>();

        ActivityInfo[] list;
        try {
            list = context.getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_ACTIVITIES).activities;

        } catch (final PackageManager.NameNotFoundException e){
            // Should never happen
            e.printStackTrace();
            list = null;
        }

        for (final ActivityInfo info : list) {
            result.add(info.name);
        }

        return result;
    }

    private boolean isActivity(Class<?> clazz){
        final boolean res;

        if (clazz == null) {
            res = false;
        } else {
            if (Activity.class.isAssignableFrom(clazz)) {
                res = true;
            } else {
                res = isApplicable(clazz.getSuperclass());
            }
        }

        return res;
    }

    protected boolean isApplicable(Class<?> clazz) {
        return isActivity(clazz) && !Modifier.isAbstract(clazz.getModifiers());
    }
}

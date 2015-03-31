package uk.co.alt236.commontests.tests.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import junit.framework.TestSuite;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 31/03/2015.
 */
public class ActivityTestBuilder extends AbstractReflectiveTestCaseBuilder {

    public ActivityTestBuilder(final Context context, final ClassFilter classFilter){
        super(classFilter);
    }

    private List<Class<?>> getAllRelevantClasses(Context context) {
        final Collection<String> classes = getClassNames();
        final List<Class<?>> methodResult = new ArrayList<Class<?>>();

        for (final String clazzName : classes) {
            final Class<?> clazz;
            try {
                clazz = Class.forName(clazzName);
                if (isApplicable(clazz)) {
                    methodResult.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return methodResult;
    }

    public TestSuite getTests(final Context context) throws Exception{
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getAllRelevantClasses(context);
        final Set<String> manifestActivities = getAllManifestActivities(context, context.getPackageName());

        for (final Class<?> clazz : listOfClasses) {
            selectedTests.addTest(new ActivityInManifestTest(clazz, manifestActivities));
        }

        for(final String clazz : manifestActivities){
            selectedTests.addTest(new ActivityInCode(clazz));
        }

        return selectedTests;
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

    private static boolean isActivity(Class<?> clazz){
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

    private static boolean isApplicable(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers()) && isActivity(clazz);
    }
}

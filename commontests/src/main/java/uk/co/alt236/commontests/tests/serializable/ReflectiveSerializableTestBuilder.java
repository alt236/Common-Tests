package uk.co.alt236.commontests.tests.serializable;

import android.content.Context;

import junit.framework.TestSuite;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.alt236.commontests.util.SerializationUtils;
import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 06/10/2014.
 */
public class ReflectiveSerializableTestBuilder extends AbstractReflectiveTestCaseBuilder {

    public ReflectiveSerializableTestBuilder(final ClassFilter classFilter){
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

    public TestSuite getTests(final Context context) {
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getAllRelevantClasses(context);

        for (final Class<?> clazz : listOfClasses) {
            selectedTests.addTest(new ReflectiveSerializableTestCase(clazz));
        }

        return selectedTests;
    }

    private static boolean isApplicable(final Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }
        if (clazz.isEnum()) {
            return false;
        }
        if (clazz.isAnonymousClass()) {
            return false;
        }

        return SerializationUtils.isSerializable(clazz);
    }
}

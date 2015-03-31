package uk.co.alt236.commontests.tests.ormlite;

import android.content.Context;

import com.j256.ormlite.table.DatabaseTable;

import junit.framework.TestSuite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 06/10/2014.
 */
public class OrmLiteSerializableTestBuilder extends AbstractReflectiveTestCaseBuilder {

    public OrmLiteSerializableTestBuilder(final ClassFilter classFilter){
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
            selectedTests.addTest(new OrmLiteSerializableTestCase(clazz));
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

        final Annotation[] annotations = clazz.getAnnotations();

        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof DatabaseTable) {
                    return true;
                }
            }
        }

        return false;
    }
}

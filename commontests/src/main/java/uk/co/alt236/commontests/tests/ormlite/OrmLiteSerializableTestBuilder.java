package uk.co.alt236.commontests.tests.ormlite;

import com.j256.ormlite.table.DatabaseTable;

import junit.framework.TestSuite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
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

    public TestSuite getTests() {
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getApplicableClasses();

        for (final Class<?> clazz : listOfClasses) {
            selectedTests.addTest(new OrmLiteSerializableTestCase(clazz));
        }

        return selectedTests;
    }

    protected boolean isApplicable(final Class<?> clazz) {
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

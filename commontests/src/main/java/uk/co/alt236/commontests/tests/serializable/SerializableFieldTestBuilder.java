package uk.co.alt236.commontests.tests.serializable;

import junit.framework.TestSuite;

import java.util.List;

import uk.co.alt236.commontests.util.SerializationUtils;
import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 06/10/2014.
 */
public class SerializableFieldTestBuilder extends AbstractReflectiveTestCaseBuilder{

    public SerializableFieldTestBuilder(final ClassFilter classFilter){
        super(classFilter);
    }

    public TestSuite getTests() {
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getApplicableClasses();

        for (final Class<?> clazz : listOfClasses) {
            selectedTests.addTest(new SerializableFieldTestCase(clazz));
        }

        return selectedTests;
    }

    protected boolean isApplicable(final Class<?> clazz) {

        if (clazz.isAnonymousClass()) {
            return false;
        }

        return SerializationUtils.isSerializable(clazz);
    }
}

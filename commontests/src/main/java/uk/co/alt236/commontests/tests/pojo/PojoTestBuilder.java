package uk.co.alt236.commontests.tests.pojo;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.commontests.util.ReflectionTestUtils;
import uk.co.alt236.commontests.util.base.AbstractReflectiveTestCaseBuilder;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 06/10/2014.
 */
public class PojoTestBuilder extends AbstractReflectiveTestCaseBuilder {
    private final ReflectionTestUtils reflectionTestUtils = new ReflectionTestUtils();

    public PojoTestBuilder(final ClassFilter classFilter){
        super(classFilter);
    }

    public TestSuite getTests() {
        final TestSuite selectedTests = new TestSuite();
        final List<Class<?>> listOfClasses = getApplicableClasses();
        final List<TestCase> stringTests = new ArrayList<TestCase>();
        final List<TestCase> hashCodeTests = new ArrayList<TestCase>();
        final List<TestCase> equalsTests = new ArrayList<TestCase>();

        for (final Class<?> clazz : listOfClasses) {
            stringTests.add(new ToStringTestCase(clazz));
            hashCodeTests.add(new HashCodeTestCase(clazz));
            equalsTests.add(new EqualsTestCase(clazz));
        }

        addTests(selectedTests, stringTests);
        addTests(selectedTests, hashCodeTests);
        addTests(selectedTests, equalsTests);

        return selectedTests;
    }

    protected boolean isApplicable(final Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }

        if (clazz.isAnonymousClass()) {
            return false;
        }

        return reflectionTestUtils.hasDefaultConstructor(clazz);
    }

    private static void addTests(final TestSuite suite, List<TestCase> tests) {
        for (TestCase test : tests) {
            suite.addTest(test);
        }
    }
}

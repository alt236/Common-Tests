package uk.co.alt236.commontests.tests.pojo;

import android.content.Context;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
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

    private boolean isApplicable(final Class<?> clazz) {
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

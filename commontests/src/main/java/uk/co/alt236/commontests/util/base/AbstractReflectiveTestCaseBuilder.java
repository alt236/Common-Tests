package uk.co.alt236.commontests.util.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.alt236.commontests.util.InstatiationException;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 31/03/2015.
 */
public abstract class AbstractReflectiveTestCaseBuilder {
    private final ClassFilter mClassFilter;

    protected AbstractReflectiveTestCaseBuilder(final ClassFilter classFilter) {
        this.mClassFilter = classFilter;
    }

    public List<Class<?>> getApplicableClasses() {
        final Collection<String> classes = mClassFilter.getFilteredClassNames();
        final List<Class<?>> methodResult = new ArrayList<>();

        Class<?> clazz;
        for (final String clazzName : classes) {
            try {
                clazz = Class.forName(clazzName);
                if (isApplicable(clazz)) {
                    methodResult.add(clazz);
                }
            } catch (final Exception e) {
                final String message = "Error while trying to instantiate '" +clazzName+ "'";
                throw new InstatiationException(message, e);
            }
        }

        return methodResult;
    }

    protected abstract boolean isApplicable(final Class<?> clazz);
}

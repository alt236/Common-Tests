package uk.co.alt236.commontests.util.base;

import java.util.Collection;

import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 31/03/2015.
 */
public abstract class AbstractReflectiveTestCaseBuilder {
    private final ClassFilter classFilter;

    protected AbstractReflectiveTestCaseBuilder(final ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    protected Collection<String> getClassNames() {
        return classFilter.getClassNames();
    }

}

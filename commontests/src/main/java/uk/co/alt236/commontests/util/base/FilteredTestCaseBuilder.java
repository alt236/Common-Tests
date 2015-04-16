package uk.co.alt236.commontests.util.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.alt236.commontests.util.InstatiationException;
import uk.co.alt236.commontests.util.filter.ClassFilter;

/**
 * Created by alexandros on 31/03/2015.
 */
public abstract class FilteredTestCaseBuilder {
    private final ClassFilter mClassFilter;

    protected FilteredTestCaseBuilder(final ClassFilter classFilter) {
        this.mClassFilter = classFilter;
    }

    protected Collection<String> getFilteredClassNames(){
        return mClassFilter.getFilteredClassNames();
    }
}

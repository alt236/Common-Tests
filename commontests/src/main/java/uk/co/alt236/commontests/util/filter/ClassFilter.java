package uk.co.alt236.commontests.util.filter;

import java.util.Collection;

/**
 * Created by alexandros on 31/03/2015.
 */
public interface ClassFilter {
    public void addClassToBlackList(final String classCanonicalName);

    public void addPackagePrefixFilter(final String prefix);

    public void clearClassBlackList();

    public void clearPackagePrefixFilter();

    public Collection<String> getClassNames();
}

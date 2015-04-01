package uk.co.alt236.commontests.util.filter;

import java.util.Collection;

/**
 * Created by alexandros on 31/03/2015.
 */
public interface ClassFilter {
    public void addPackagePrefixFilter(final String prefix);

    public void addToBlacklist(final String className);

    public void clearBlacklist();

    public void clearPackagePrefixFilter();

    public int getBlacklistSize();

    public int getPackagePrefixFilterSize();

    public Collection<String> getFilteredClassNames();
}

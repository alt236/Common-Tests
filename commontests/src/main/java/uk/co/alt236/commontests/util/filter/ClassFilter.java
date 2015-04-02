package uk.co.alt236.commontests.util.filter;

import java.util.Collection;

/**
 * Created by alexandros on 31/03/2015.
 */
public interface ClassFilter {
    public void addPackagePrefixWhitelist(final String prefix);

    public void addPackagePrefixBlacklist(final String prefix);

    public void addToClassBlacklist(final String className);

    public void clearClassBlacklist();

    public void clearPackagePrefixWhitelist();

    public void clearPackagePrefixBlacklist();

    public int getClassBlacklistSize();

    public int getPackagePrefixWhitelistSize();

    public int getPackagePrefixBlacklistSize();

    public Collection<String> getFilteredClassNames();
}

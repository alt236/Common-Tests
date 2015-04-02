package uk.co.alt236.commontests.util.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandros on 01/04/2015.
 */
public class SimpleClassFilter implements ClassFilter{
    private final Set<String> mPackagePrefixWhitelist = new HashSet<>();
    private final Set<String> mPackagePrefixBlacklist = new HashSet<>();
    private final Set<String> mClassBlacklist = new HashSet<>();
    private final Set<String> mClassNames = new HashSet<>();

    public SimpleClassFilter(){
        // NOTHING;
    }

    public SimpleClassFilter(final Collection<String> classes){
        addClasses(classes);
    }

    public void addClass(final String classes){
        mClassNames.add(classes);
    }

    public void addClasses(final Collection<String> className){
        mClassNames.addAll(className);
    }

    @Override
    public void addPackagePrefixWhitelist(final String prefix) {
        mPackagePrefixWhitelist.add(prefix);
    }

    @Override
    public void addPackagePrefixBlacklist(final String prefix) {
        mPackagePrefixBlacklist.add(prefix);
    }

    @Override
    public void addToClassBlacklist(final String className) {
        mClassBlacklist.add(className);
    }

    @Override
    public void clearClassBlacklist() {
        mClassBlacklist.clear();
    }

    @Override
    public void clearPackagePrefixWhitelist() {
        mPackagePrefixWhitelist.clear();
    }

    @Override
    public void clearPackagePrefixBlacklist() {
        mPackagePrefixBlacklist.clear();
    }

    @Override
    public int getClassBlacklistSize(){
        return mClassBlacklist.size();
    }

    @Override
    public int getPackagePrefixWhitelistSize(){
        return mPackagePrefixWhitelist.size();
    }

    @Override
    public int getPackagePrefixBlacklistSize(){
        return mPackagePrefixBlacklist.size();
    }

    @Override
    public Collection<String> getFilteredClassNames() {
        final Collection<String> methodResult = new HashSet<>();

        for(final String className : mClassNames){
            if (isPackageWhitelisted(className)
                    && !isPackageBlacklisted(className)
                    && !isClassBlackListed(className)) {
                methodResult.add(className);
            }
        }

        return methodResult;
    }

    private boolean isClassBlackListed(final String className) {
        if(mClassBlacklist.isEmpty()){return false;}

        return mClassBlacklist.contains(className);
    }

    private boolean isPackageWhitelisted(final String className) {
        if(mPackagePrefixWhitelist.isEmpty()){return true;}

        for (final String prefix : mPackagePrefixWhitelist) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPackageBlacklisted(final String className) {
        if(mPackagePrefixBlacklist.isEmpty()){return false;}

        for (final String prefix : mPackagePrefixBlacklist) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}

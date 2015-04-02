package uk.co.alt236.commontests.util.filter;

import android.util.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandros on 01/04/2015.
 */
public class SimpleClassFilter implements ClassFilter{
    private final Set<String> mPackageNamePrefix = new HashSet<>();
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
    public void addPackagePrefixFilter(final String prefix) {
        mPackageNamePrefix.add(prefix);
    }

    @Override
    public void addToBlacklist(final String className) {
        mClassBlacklist.add(className);
    }

    @Override
    public void clearBlacklist() {
        mClassBlacklist.clear();
    }

    @Override
    public void clearPackagePrefixFilter() {
        mPackageNamePrefix.clear();
    }

    @Override
    public int getBlacklistSize(){
        return mClassBlacklist.size();
    }

    @Override
    public int getPackagePrefixFilterSize(){
        return mPackageNamePrefix.size();
    }

    @Override
    public Collection<String> getFilteredClassNames() {
        final Collection<String> methodResult = new HashSet<>();

        for(final String className : mClassNames){
            if (isFromValidPackage(className) && !isBlackListed(className)) {
                methodResult.add(className);
            }
        }

        return methodResult;
    }

    private boolean isBlackListed(final String className) {
        if(mClassBlacklist.isEmpty()){return false;}

        return mClassBlacklist.contains(className);
    }

    private boolean isFromValidPackage(final String className) {
        if(mPackageNamePrefix.isEmpty()){return true;}

        for (final String prefix : mPackageNamePrefix) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}

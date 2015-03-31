package uk.co.alt236.commontests.util.filter;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by alexandros on 31/03/2015.
 */
public class DexClassFilter implements ClassFilter {
    private final Context context;
    private final Set<String> validPackageNamePrefix = new HashSet<String>();
    private final Set<String> classBlackList = new HashSet<String>();

    public DexClassFilter(final Context context) {
        this.context = context;
    }

    public void addClassToBlackList(final String classCanonicalName) {
        classBlackList.add(classCanonicalName);
    }

    public void addPackagePrefixFilter(final String prefix) {
        validPackageNamePrefix.add(prefix);
    }

    public void clearClassBlackList() {
        validPackageNamePrefix.clear();
    }

    public void clearPackagePrefixFilter() {
        validPackageNamePrefix.clear();
    }

    public Collection<String> getClassNames() {
        final DexFile df = getDexFile();
        final List<String> methodResult = new ArrayList<String>();

        for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
            final String clazzName = iter.nextElement();
            if (isFromValidPackage(clazzName) && !isBlackListed(clazzName)) {
                methodResult.add(clazzName);
            }
        }

        return methodResult;
    }

    private DexFile getDexFile() {
        try {
            final String packageCodePath = context.getPackageCodePath();
            return new DexFile(packageCodePath);
        } catch (IOException e) {
            throw new IllegalStateException("Error while getting the DEX file!", e);
        }
    }

    private boolean isBlackListed(final String className) {
        if (classBlackList.size() == 0) {
            return true;
        }

        for (final String str : classBlackList) {
            if (className.equals(str)) {
                return true;
            }
        }

        return false;
    }

    private boolean isFromValidPackage(final String className) {
        if (validPackageNamePrefix.size() == 0) {
            return true;
        }

        for (final String str : validPackageNamePrefix) {
            if (className.startsWith(str)) {
                return true;
            }
        }

        return false;
    }
}

package uk.co.alt236.commontests.util;

import android.content.Context;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by alexandros on 02/04/2015.
 */
public final class DexUtils {

    public static Collection<String> getClassNamesFromDex(final Context context){
        final DexFile dexFile = getDexFile(context);

        final Set<String> methodResult = new HashSet<>();

        for (Enumeration<String> iterator = dexFile.entries(); iterator.hasMoreElements(); ) {
            final String clazzName = iterator.nextElement();
            methodResult.add(clazzName);
        }

        return Collections.unmodifiableCollection(methodResult);
    }

    public static DexFile getDexFile(final Context context) {
        try {
            final String packageCodePath = context.getPackageCodePath();
            return new DexFile(packageCodePath);
        } catch (IOException e) {
            throw new IllegalStateException("Error while getting the DEX file!", e);
        }
    }
}

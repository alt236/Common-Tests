package uk.co.alt236.commontests.util.filter;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by alexandros on 31/03/2015.
 */
public class DexClassFilter extends SimpleClassFilter {

    public DexClassFilter(final Context context) {
       final DexFile dexFile = getDexFile(context);

        final List<String> methodResult = new ArrayList<>();

        for (Enumeration<String> iterator = dexFile.entries(); iterator.hasMoreElements(); ) {
            final String clazzName = iterator.nextElement();
            methodResult.add(clazzName);
        }

        addClasses(methodResult);
    }

    private static DexFile getDexFile(final Context context) {
        try {
            final String packageCodePath = context.getPackageCodePath();
            return new DexFile(packageCodePath);
        } catch (IOException e) {
            throw new IllegalStateException("Error while getting the DEX file!", e);
        }
    }
}

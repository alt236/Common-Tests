package uk.co.alt236.commontests.util.filter;

import junit.framework.TestCase;

public class SimpleClassFilterTest extends TestCase {

    public void testBlacklist() throws Exception {
        final String className = SimpleClassFilterTest.class.getName();
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addClass(className);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addToBlacklist(className);
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.clearBlacklist();
        assertEquals(1, filter.getFilteredClassNames().size());
    }

    public void testPrefixFilter() throws Exception {
        final String classPrefixValid = "com.sample.";
        final String classPrefixInValid = "com.sample2.";
        final String className = classPrefixValid + "example.Class";

        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addClass(className);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixFilter(classPrefixValid);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.clearPackagePrefixFilter();
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixFilter(classPrefixInValid);
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addPackagePrefixFilter(classPrefixValid);
        assertEquals(1, filter.getFilteredClassNames().size());
    }

    public void testGetBlacklistSize() throws Exception {
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getBlacklistSize());

        filter.addToBlacklist("com.test");
        assertEquals(1, filter.getBlacklistSize());

        filter.addToBlacklist("com.test1");
        assertEquals(2, filter.getBlacklistSize());

        filter.clearBlacklist();
        assertEquals(0, filter.getBlacklistSize());
    }

    public void testGetPackagePrefixFilterSize() throws Exception {
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getPackagePrefixFilterSize());

        filter.addPackagePrefixFilter("com.test");
        assertEquals(1, filter.getPackagePrefixFilterSize());

        filter.addPackagePrefixFilter("com.test1");
        assertEquals(2, filter.getPackagePrefixFilterSize());

        filter.clearPackagePrefixFilter();
        assertEquals(0, filter.getPackagePrefixFilterSize());
    }
}
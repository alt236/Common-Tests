package uk.co.alt236.commontests.util.filter;

import junit.framework.TestCase;

public class SimpleClassFilterTest extends TestCase {

    public void testBlacklist() throws Exception {
        final String className = SimpleClassFilterTest.class.getName();
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addClass(className);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addToClassBlacklist(className);
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.clearClassBlacklist();
        assertEquals(1, filter.getFilteredClassNames().size());
    }

    public void testGetBlacklistSize() throws Exception {
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getClassBlacklistSize());

        filter.addToClassBlacklist("com.test");
        assertEquals(1, filter.getClassBlacklistSize());

        filter.addToClassBlacklist("com.test1");
        assertEquals(2, filter.getClassBlacklistSize());

        filter.clearClassBlacklist();
        assertEquals(0, filter.getClassBlacklistSize());
    }

    public void testGetPackagePrefixWhitelistSize() throws Exception {
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getPackagePrefixWhitelistSize());

        filter.addPackagePrefixWhitelist("com.test");
        assertEquals(1, filter.getPackagePrefixWhitelistSize());

        filter.addPackagePrefixWhitelist("com.test1");
        assertEquals(2, filter.getPackagePrefixWhitelistSize());

        filter.clearPackagePrefixWhitelist();
        assertEquals(0, filter.getPackagePrefixWhitelistSize());
    }

    public void testGetPackagePrefixBlacklistSize() throws Exception {
        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getPackagePrefixBlacklistSize());

        filter.addPackagePrefixBlacklist("com.test");
        assertEquals(1, filter.getPackagePrefixBlacklistSize());

        filter.addPackagePrefixBlacklist("com.test1");
        assertEquals(2, filter.getPackagePrefixBlacklistSize());

        filter.clearPackagePrefixBlacklist();
        assertEquals(0, filter.getPackagePrefixBlacklistSize());
    }

    public void testPrefixWhitelist() throws Exception {
        final String classPrefixValid = "com.sample.";
        final String classPrefixInvalid = "com.sample2.";
        final String className = classPrefixValid + "example.Class";

        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addClass(className);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixWhitelist(classPrefixValid);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.clearPackagePrefixWhitelist();
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixWhitelist(classPrefixInvalid);
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addPackagePrefixWhitelist(classPrefixValid);
        assertEquals(1, filter.getFilteredClassNames().size());
    }

    public void testPrefixBlacklist() throws Exception {
        final String classPrefixValid = "com.sample.";
        final String classPrefixInValid = "com.sample2.";
        final String className = classPrefixValid + "example.Class";

        final SimpleClassFilter filter = new SimpleClassFilter();
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.addClass(className);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixBlacklist(classPrefixValid);
        assertEquals(0, filter.getFilteredClassNames().size());

        filter.clearPackagePrefixBlacklist();
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixBlacklist(classPrefixInValid);
        assertEquals(1, filter.getFilteredClassNames().size());

        filter.addPackagePrefixBlacklist(classPrefixValid);
        assertEquals(0, filter.getFilteredClassNames().size());
    }
}
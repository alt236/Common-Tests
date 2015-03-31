package uk.co.alt236.commontests.tests.android.parcelable;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.test.AndroidTestCase;

import uk.co.alt236.commontests.util.ReflectionTestUtils;

/**
 * Created by alexandros on 05/10/2014.
 */
public class ReflectiveParcelableTestCase extends AndroidTestCase {
    private final static String BUNDLE_TAG = "this_is_a_tag";

    private final Class<?> clazz;

    public ReflectiveParcelableTestCase(Class clazz) {
        setName("testReflectively");
        this.clazz = clazz;
    }

    public void testReflectively() throws Exception {
        final String clazzName = clazz.getName();
        final ReflectionTestUtils utils = new ReflectionTestUtils();

        final Parcelable parcelableIn =
                (Parcelable) utils.getUnprotectedDefaultConstructor(clazz).newInstance(new Object[0]);
        final Bundle bundleIn = new Bundle();
        final Bundle bundleOut;
        final Parcelable parcelableOut;

        bundleIn.putParcelable(BUNDLE_TAG, parcelableIn);

        //Save bundle to parcel
        final Parcel parcel = Parcel.obtain();
        bundleIn.writeToParcel(parcel, 0);

        //Extract bundle from parcel
        parcel.setDataPosition(0);

        bundleOut = parcel.readBundle();
        bundleOut.setClassLoader(this.getClass().getClassLoader());
        parcelableOut = bundleOut.getParcelable(BUNDLE_TAG);

        //Check that objects are not same and test that objects are equal
        assertFalse("Bundle is the same: " + clazzName, bundleIn == bundleOut);
        assertFalse("Parcelable is the same: " + clazzName, parcelableIn == parcelableOut);
        assertTrue("Parcelables aren't equal: " + clazzName, DeepEquals.deepEquals(parcelableIn, parcelableOut));
        assertEquals("describeContents() for in object should be 0: " + clazzName, 0, parcelableIn.describeContents());
        assertEquals("describeContents() for out object should be 0: " + clazzName, 0, parcelableOut.describeContents());
    }

}

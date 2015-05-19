package com.mobile.hw.filessearch;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created with Android Studio.
 * User: ihaiwen
 * Date: 5/19/15
 * Time: 3:29 PM
 * <p/>
 * TODO: Add a header comment!
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ObjectFactoryTest {

    @Test
    public void createSearchOperation_shouldNotNull() {
        Assert.assertNotNull(ObjectFactory.createSearchOperation());
    }

    @Test
    public void createSettingContent_shouldNotNull() {
        Assert.assertNotNull(ObjectFactory.createSettingContent());
    }
}

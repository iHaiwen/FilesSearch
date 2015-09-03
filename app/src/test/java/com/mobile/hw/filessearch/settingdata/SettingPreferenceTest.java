package com.mobile.hw.filessearch.settingdata;

import com.mobile.hw.filessearch.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created with Android Studio.
 * User: ihaiwen
 * Date: 6/2/15
 * Time: 8:13 PM
 * <p/>
 * TODO: Add a header comment!
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class SettingPreferenceTest {

    private SettingPreference settingPreference;

    @Before
    public void setUp() throws Exception {
        settingPreference = new SettingPreference();
    }
}

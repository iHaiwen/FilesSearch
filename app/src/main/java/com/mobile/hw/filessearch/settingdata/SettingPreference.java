package com.mobile.hw.filessearch.settingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.mobile.hw.filessearch.app.BaseApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 1/25/15
 * Time: 6:22 PM
 *
 * 保存数据在xml文件上
 */
public class SettingPreference implements SettingContent {

    private final ArrayList<String> directoryPathArrayList = new ArrayList<String>(3);

    private SharedPreferences sharedPreferences;

    private final String SHARED_PREFERENCES_NAME = "SETTING_CONTEXT";
    private final String DIRECTORY_PATHS_KEY = "DIRECTORY_PATHS";
    private final String AUTO_OPEN_WHEN_SINGLE_FILE_KEY = "AUTO_OPEN_WHEN_SINGLE_FILE";


    public SettingPreference() {
        readPreferenceData();
    }

    @Override
    public List<String> needSearchDirectoryPaths() {
        return directoryPathArrayList;
    }

    @Override
    public boolean addSearchDirectoryPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        //
        directoryPathArrayList.add(path);
        updatePreferenceData();
        return true;
    }

    @Override
    public boolean clean() {
        preferences().edit().clear().apply();
        return true;
    }

    /*
     * private methods
     */

    private Context applicationContext() {
        return BaseApplication.getInstance().getApplicationContext();
    }

    private SharedPreferences preferences() {
        if (sharedPreferences == null) {
            sharedPreferences = applicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }

    private void readPreferenceData() {
        Set<String> stringSet = preferences().getStringSet(DIRECTORY_PATHS_KEY, null);
        if (stringSet != null) {
            directoryPathArrayList.addAll(stringSet);
        }
    }

    private void updatePreferenceData() {
        SharedPreferences.Editor editor = preferences().edit();

        if (directoryPathArrayList.isEmpty()) {
            editor.remove(DIRECTORY_PATHS_KEY).apply();
        } else {
            Set<String> set = new HashSet<String>();
            set.addAll(directoryPathArrayList);
            editor.putStringSet(DIRECTORY_PATHS_KEY, set).apply();
        }

    }
}

package com.mobile.hw.filessearch;


import com.mobile.hw.filessearch.search.SearchFileOperation;
import com.mobile.hw.filessearch.search.SearchFilesLogic;
import com.mobile.hw.filessearch.store.SettingContent;
import com.mobile.hw.filessearch.store.SettingPreference;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 1/25/15
 * Time: 6:22 PM
 *
 * 工厂
 */
public class ObjectFactory {

    private static SettingContent settingContent = null;
    private static SearchFileOperation searchOperation = null;

    public static synchronized SettingContent createSettingContent() {
        if (settingContent == null) {
            settingContent = new SettingPreference();
        }

        return settingContent;
    }

    public static synchronized SearchFileOperation createSearchOperation() {
        if (searchOperation == null) {
            searchOperation = new SearchFilesLogic(null);
        }

        return searchOperation;
    }

}

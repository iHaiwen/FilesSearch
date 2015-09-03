package com.mobile.hw.filessearch.settingdata;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 1/25/15
 * Time: 6:17 PM
 *
 * 设置的内容
 */
public interface SettingContent {

    public List<String> needSearchDirectoryPaths();

    public boolean addSearchDirectoryPath(String path);

    public boolean clean();
}

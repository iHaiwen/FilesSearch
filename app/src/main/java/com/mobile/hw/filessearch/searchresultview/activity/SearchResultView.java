package com.mobile.hw.filessearch.searchresultview.activity;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 10/2/14
 * Time: 2:22 PM
 * 搜索页面
 */
public interface SearchResultView {

    public void setTile(String title);

    public void showWaitingView(String message);

    public void dismissWaitingView();

    public void setResult(List<File> files);
}

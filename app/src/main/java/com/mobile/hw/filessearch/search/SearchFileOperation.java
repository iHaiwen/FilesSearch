package com.mobile.hw.filessearch.search;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 1/25/15
 * Time: 6:46 PM
 *
 * 搜索处理
 */
public interface SearchFileOperation {

    public void setSearchFilesListener(SearchFilesListener searchListener);

    public SearchFilesListener getSearchFilesListener();

    public void searchFileByKeywordOnDirectories(String keyword, List<String> directoryPaths);

    public String getSearchingKeyword();

//    public boolean searching();
//
//    public void forceStop();
}

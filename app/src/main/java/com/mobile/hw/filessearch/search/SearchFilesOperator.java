package com.mobile.hw.filessearch.search;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 10/2/14
 * Time: 11:03 AM
 * 搜索符合名字的文件
 * 建议在非主线程中调用
 */
public class SearchFilesOperator implements SearchFileOperation{

    private SearchFilesListener searchFilesListener;

    private final ArrayList<File> matchedFileArrayList = new ArrayList<>(20);
    private String searchKeyword;


    public SearchFilesOperator() {
        this(null);
    }

    public SearchFilesOperator(SearchFilesListener searchFilesListener) {
        this.searchFilesListener = searchFilesListener;
    }

    @Override
    public void setSearchFilesListener(SearchFilesListener searchFilesListener) {
        this.searchFilesListener = searchFilesListener;
    }

    @Override
    public SearchFilesListener getSearchFilesListener() {
        return this.searchFilesListener;
    }

    @Override
    public void searchFileByKeywordOnDirectories(String keyword, List<String> directoryPaths) {
        this.searchKeyword = keyword;
        matchedFileArrayList.clear();
        if (directoryPaths == null || directoryPaths.isEmpty()) {
            return;
        }

        //
        for (String filePath : directoryPaths) {
            searchFileByKeywordOnPath(keyword, filePath);
        }
    }

    @Override
    public String getSearchingKeyword() {
        return searchKeyword;
    }





    /*
    private methods
     */



    private void searchFileByKeywordOnPath(String keyword, String parentDirPath) {
        callbackWhenSearchStart();

        //
        if (TextUtils.isEmpty(keyword)) {
            callbackWhenSearchEnd();
            return;
        }

        //
        File parentDir = new File(parentDirPath);
        if (!parentDir.exists()) {
            callbackWhenSearchEnd();
            return;
        }

        //
        if (!parentDir.isDirectory()) {
            callbackWhenSearchEnd();
            return;
        }

        //
        matchedFileArrayList.addAll(Arrays.asList(parentDir.listFiles(new MatchedFileFilter(keyword))));
        callbackWhenSearchEnd();
    }


    //call back

    private void callbackWhenSearchStart() {
        if (searchFilesListener != null) {
            searchFilesListener.onSearchFileStart(this);
        }
    }

    private void callbackWhenSearchEnd() {
        if (searchFilesListener != null) {
            searchFilesListener.onFilesSearchEnd(this, matchedFileArrayList);
        }
    }
}

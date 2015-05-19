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
public class SearchFilesLogic implements SearchFileOperation{

    public SearchFilesListener searchFilesListener;

    private final ArrayList<File> matchedFileArrayList = new ArrayList<>(20);
    private String searchKeyword;
    private boolean forceStop;


    public SearchFilesLogic(SearchFilesListener searchFilesListener) {
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

    }

    @Override
    public boolean searching() {
        return false;
    }

    @Override
    public void forceStop() {

    }

    public void searchFileByKeywordOnPath(String keyword, String parentDirPath) {
        forceStop = false;
        this.searchKeyword = keyword;
        matchedFileArrayList.clear();
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
        //findMatchedFilesOnDirectory(parentDir);
        callbackWhenSearchEnd();
    }

//    public void stopSearching() {
//        forceStop = true;
//    }


    /*
    private methods
     */


    private void findMatchedFilesOnDirectory(File directory) {
        File[] listFiles = directory.listFiles();
        for (File aFile : listFiles) {
            if (forceStop) {
                break;
            }

            //
            if (aFile.isDirectory()) {
                findMatchedFilesOnDirectory(aFile);
            }
            else {
                String fileName = aFile.getName();
                if (fileName.contains(searchKeyword)) {
                    matchedFileArrayList.add(aFile);
                    callbackWhenFindMatchedFile();
                }
            }
        }
    }


    //call back

    private void callbackWhenSearchStart() {
        if (searchFilesListener != null) {
            searchFilesListener.onSearchFileStart(this);
        }
    }

    private void callbackWhenSearchEnd() {
        if (searchFilesListener != null) {
            searchFilesListener.onFilesSearchEnd(this, matchedFileArrayList, true);
        }
    }

    private void callbackWhenFindMatchedFile() {
        if (searchFilesListener != null) {
            searchFilesListener.onSearchFileResultChange(this, matchedFileArrayList);
        }
    }
}

package com.mobile.hw.filessearch.search;

import com.mobile.hw.filessearch.BuildConfig;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: ihaiwen
 * Date: 5/28/15
 * Time: 10:28 PM
 * <p/>
 * TODO: Add a header comment!
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class SearchFilesOperatorTest {

    private SearchFilesOperator searchOperator;

    @Before
    public void setUp() throws Exception {
        searchOperator = new SearchFilesOperator();
    }

    @Test
    public void setSearchFilesListener_shouldNotNull() throws Exception {
        SearchFilesListener searchListener = new SearchFilesListener() {
            @Override
            public void onSearchFileStart(SearchFileOperation searchOperation) {

            }

            @Override
            public void onSearchFileResultChange(SearchFileOperation searchOperation, List<File> matchedFilePaths) {

            }

            @Override
            public void onFilesSearchEnd(SearchFileOperation searchOperation, List<File> matchedFilePaths, boolean forceStop) {

            }
        };
        searchOperator.setSearchFilesListener(searchListener);
        Assert.assertEquals(searchListener, searchOperator.getSearchFilesListener());
    }

    @Test
    public void searchNotExistFiles_shouldNotNull() throws Exception {
        ArrayList<String> testPathList = new ArrayList<>(1);
        testPathList.add("/data/data/");
        SearchFilesListener searchListener = new SearchFilesListener() {
            @Override
            public void onSearchFileStart(SearchFileOperation searchOperation) {

            }

            @Override
            public void onSearchFileResultChange(SearchFileOperation searchOperation, List<File> matchedFilePaths) {

            }

            @Override
            public void onFilesSearchEnd(SearchFileOperation searchOperation, List<File> matchedFilePaths, boolean forceStop) {
                Assert.assertEquals(0, matchedFilePaths.size());
                Assert.assertEquals(false, forceStop);
            }
        };

        searchOperator.setSearchFilesListener(searchListener);
        searchOperator.searchFileByKeywordOnDirectories("4", testPathList);
    }

    @Test
    public void searchExistFiles_returnOneFile() throws Exception {
        ArrayList<String> directoryPaths = new ArrayList<>(1);
        directoryPaths.add("/data/data");
        SearchFilesListener searchListener = new SearchFilesListener() {
            @Override
            public void onSearchFileStart(SearchFileOperation searchOperation) {

            }

            @Override
            public void onSearchFileResultChange(SearchFileOperation searchOperation, List<File> matchedFilePaths) {

            }

            @Override
            public void onFilesSearchEnd(SearchFileOperation searchOperation, List<File> matchedFilePaths, boolean forceStop) {
                Assert.assertEquals(1, matchedFilePaths.size());
            }
        };

        searchOperator.setSearchFilesListener(searchListener);
        searchOperator.searchFileByKeywordOnDirectories("111", directoryPaths);
    }
}

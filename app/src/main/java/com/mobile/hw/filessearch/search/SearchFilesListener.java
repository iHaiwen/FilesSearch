package com.mobile.hw.filessearch.search;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 10/2/14
 * Time: 11:04 AM
 */
public interface SearchFilesListener {

    public void onSearchFileStart(SearchFileOperation searchOperation);

//    public void onSearchFileResultChange(SearchFileOperation searchOperation,
//            List<File> matchedFilePaths);

    public void onFilesSearchEnd(SearchFileOperation searchOperation, List<File> matchedFilePaths);
}

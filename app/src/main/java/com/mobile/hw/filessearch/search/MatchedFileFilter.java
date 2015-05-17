package com.mobile.hw.filessearch.search;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 10/2/14
 * Time: 2:37 PM
 */
public class MatchedFileFilter implements FilenameFilter {

    private String keyword;

    public MatchedFileFilter(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean accept(File dir,String name) {
        return name.contains(keyword);
    }
}

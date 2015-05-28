package com.mobile.hw.filessearch.barcodeview.presenter;


import com.mobile.hw.filessearch.app.BasePresenter;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 10/2/14
 * Time: 2:17 PM
 * 搜索界面对应的presenter
 */
public interface SearchResultPresenter extends BasePresenter {

    public void searchFilesByWord(String word);

    public void stopSearch();
}

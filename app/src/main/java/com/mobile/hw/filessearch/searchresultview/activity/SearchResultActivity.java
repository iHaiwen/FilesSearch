package com.mobile.hw.filessearch.searchresultview.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobile.hw.filessearch.R;
import com.mobile.hw.filessearch.app.BaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 8/10/14
 * Time: 9:37 PM
 * 搜索结果
 *
 *  url scheme {
 *      filesearch://search?keyword=
 *
 *      keyword 搜索关键字
 *  }
 */
public class SearchResultActivity extends BaseActivity {

    private EditText keywordEditText;
    private TextView nonDataTextView;
    private ListView searchResultListView;
    private View searchButton;

    private String keyword;
    private boolean isFirstVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initViews();
        initParams();
        initEvents();
    }

    @Override
    public void onResume() {
        super.onResume();

        //
        if (isFirstVisible) {
            isFirstVisible = false;

            searchAction();
        }

        //
        keywordEditText.setText(keyword);
    }


    /*
    private methods
     */


    private void initViews() {
        super.setTitle("搜索结果");

        //
        keywordEditText = (EditText)findViewById(R.id.search_text_edittext);
        nonDataTextView = (TextView)findViewById(R.id.non_result_textview);
        searchResultListView = (ListView)findViewById(R.id.search_result_listview);
        searchButton = findViewById(R.id.search_button);
    }

    private void initParams() {
        isFirstVisible = true;
        keyword = getIntent().getData().getQueryParameter("keyword");
    }

    private void initEvents() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = keywordEditText.getText().toString();
                searchAction();
            }
        });

        //
        setBackButton(View.VISIBLE, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //
        setRightButton(View.GONE, null, null);
    }

    //

    private void searchAction() {
        if (TextUtils.isEmpty(keyword)) {
            showMessageInToast("搜索不能为空哦！");
            return;
        }

        //
        searchKeyword(keywordEditText.getText().toString());
    }

    private void searchKeyword(String word) {
        showProgressDialog("正在搜索...", new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dismissDialog();
            }
        });
    }

    //common

    private void showMessageInToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
package com.mobile.hw.filessearch.ui.app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.mobile.hw.filessearch.R;
import com.mobile.hw.filessearch.ui.view.widget.BeautifulProgressDialog;
import com.mobile.hw.filessearch.ui.view.widget.CustomImageButton;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 8/10/14
 * Time: 5:14 PM
 * Activity的基类, 继承于FragmentActivity
 */
public class BaseActivity extends FragmentActivity {

    private CustomImageButton backButton;
    private TextView titleTextView;
    private TextView rightTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_basic);

        //
        initViews();
    }

    @Override
    public void onDestroy() {
        isDestroyed = false;

        super.onDestroy();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        titleTextView.setText(title);
    }

    protected void setBackButton(int visibility, View.OnClickListener listener) {
        backButton.setVisibility(visibility);
        backButton.setOnClickListener(listener);
    }

    protected void setRightButton(int visibility, String title, View.OnClickListener listener) {
        rightTextView.setVisibility(visibility);
        rightTextView.setText(title);
        rightTextView.setOnClickListener(listener);
    }

    // Dialog 模块
    // 简单实现Dialog功能
    protected boolean isDestroyed = false;
    private DialogInterface.OnCancelListener childCancelListener;
    private Dialog managedDialog;

    /**
     * 显示Progress Dialog.
     *
     * @param title
     * @param cancelListener
     */
    protected void showProgressDialog(String title, DialogInterface.OnCancelListener cancelListener) {
        if (isDestroyed)
            return;
        dismissDialog();

        BeautifulProgressDialog dlg = new BeautifulProgressDialog(this);
        dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (childCancelListener != null) {
                    childCancelListener.onCancel(dialog);
                    childCancelListener = null;
                }
                managedDialog = null;
            }
        });
        dlg.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH)
                    return true;
                return false;
            }
        });
        dlg.setMessage(title == null ? "载入中..." : title);

        managedDialog = dlg;
        childCancelListener = cancelListener;
        dlg.show();
    }

    protected void dismissDialog() {
        if (isDestroyed)
            return;
        if (managedDialog != null) {
            if (managedDialog.isShowing())
                managedDialog.dismiss();
            managedDialog = null;
            childCancelListener = null;
        }
    }



    /*
    private methods
     */

    private void initViews() {
        initTitleView();

        //
        backButton = (CustomImageButton) findViewById(R.id.title_left_button);
        rightTextView = (TextView) findViewById(R.id.title_right_button);
        titleTextView = (TextView) findViewById(android.R.id.title);
    }

    private void initTitleView() {
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_custom);
    }
}
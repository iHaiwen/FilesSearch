package com.mobile.hw.filessearch.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.mobile.hw.filessearch.R;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 8/10/14
 * Time: 7:40 PM
 * 条码输入界面
 */
public class BarcodeInputFragment extends Fragment {

    private View rootView;
    private EditText contentEditText;
    private View searchButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_barcode_input, container, false);

        //
        initViews();
        initEvents();

        //
        return rootView;
    }


    /*
    private methods
     */


    private void initViews() {
        contentEditText = (EditText)rootView.findViewById(R.id.input_edittext);
        searchButton = rootView.findViewById(R.id.confirm_button);
    }

    private void initEvents() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressConfirmButtonAction();
            }
        });
    }

    private void pressConfirmButtonAction() {
        String inputContent = contentEditText.getText().toString();
        if (TextUtils.isEmpty(inputContent)) {
            showMessageInToast("请输入条码内容");
            return;
        }

        //
        openSearchView(inputContent);
    }

    private void openSearchView(String keyword){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("filesearch://search?keyword=" + keyword));
        this.getActivity().startActivity(intent);
    }

    //common

    private void showMessageInToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
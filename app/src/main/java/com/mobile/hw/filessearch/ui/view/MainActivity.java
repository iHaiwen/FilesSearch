package com.mobile.hw.filessearch.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.google.zxing.Result;
import com.mobile.hw.filessearch.R;
import com.mobile.hw.filessearch.R;
import com.mobile.hw.filessearch.ui.app.BaseActivity;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

public class MainActivity extends BaseActivity {

//    private final int TYPE_FRAGMENT_CONTENT_SCANNER = 0;
//    private final int TYPE_FRAGMENT_CONTENT_INPUT = 1;

    private boolean firstVisible;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //
        initViews();
        initParams();
        initEvents();
    }

    @Override
    public void onResume() {
        super.onResume();

        //
        if (firstVisible) {
            firstVisible = false;

            //
            //fragmentContentType = TYPE_FRAGMENT_CONTENT_SCANNER;
            switchToScanner();
        }
    }


    /*
    private methods
     */


    private void initViews() {
        super.setTitle("扫描");
    }

    private void initParams() {
        firstVisible = true;
    }

    private void initEvents() {
        setBackButton(View.GONE, null);
    }

    //

    private void switchToScanner() {
        super.setTitle("条码/二维码");

        setRightButton(View.VISIBLE, "条码输入", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToBarcodeInput();
            }
        });

        //
        showScannerView();
    }

    private void showScannerView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag("loadedFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        BarCodeScannerFragment barCodeScannerFragment = new BarCodeScannerFragment();
        barCodeScannerFragment.setmCallBack(scanResultCallback);
        fragmentTransaction.add(R.id.fragment_content_layout, barCodeScannerFragment, "loadedFragment");

        fragmentTransaction.commitAllowingStateLoss();
    }

    //scan methods

    private BarCodeScannerFragment.IResultCallback scanResultCallback = new BarCodeScannerFragment.IResultCallback() {
        @Override
        public void result(Result lastResult) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("filesearch://search?keyword=" + lastResult.toString()));
            startActivity(intent);
        }
    };

    private void switchToBarcodeInput() {
        super.setTitle("条码输入");

        setRightButton(View.VISIBLE, "扫码", new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switchToScanner();
            }
        });

        //
        showBarcodeInputView();
    }

    private void showBarcodeInputView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag("loadedFragment");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.add(R.id.fragment_content_layout, new BarcodeInputFragment(), "loadedFragment");

        fragmentTransaction.commitAllowingStateLoss();
    }
}

package com.mobile.hw.filessearch.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobile.hw.filessearch.R;
import com.mobile.hw.filessearch.util.Utils;

public class BeautifulProgressDialog extends AlertDialog implements android.view.View.OnClickListener {

	Context mContext;
	private View mDivider;
	private View mContent;
	private TextView mMessageText;
	private ImageView mBtnCancel;
	private CharSequence mMesssage;
	
	public BeautifulProgressDialog(Context context) {
		super(context, R.style.dialog_fullscreen);
		
	}
	
	public BeautifulProgressDialog(Context context, int theme) {
		super(context, R.style.dialog_fullscreen);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
	}
	
	protected void setupView() {
		setContentView(R.layout.beautiful_progress_dialog);
		mDivider = findViewById(R.id.divider);
		mContent = findViewById(R.id.content);
		mMessageText = (TextView) findViewById(R.id.message);
		mMessageText.setText(mMesssage);
		mBtnCancel = (ImageView) findViewById(R.id.btn_cancel);
		mBtnCancel.setOnClickListener(this);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return super.onKeyDown(keyCode, event);
		} 
		return false;
	}

    public void setCancelable(boolean flag) {
    	super.setCancelable(flag);
    	if (flag) {
    		mDivider.setVisibility(View.VISIBLE);
    		mBtnCancel.setVisibility(View.VISIBLE);
    	} else {
    		mDivider.setVisibility(View.GONE);
    		mBtnCancel.setVisibility(View.GONE);
    	}
    }
    
    @Override
    public void setMessage(CharSequence message) {
    	super.setMessage(message);
    	mMesssage = message;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			cancel();
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!Utils.isPointInsideView(event.getX(), event.getY(), mContent)) {
			return true;
		}
		return super.onTouchEvent(event);
	}
}

package com.example.basepackagelib.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import com.example.basepackagelib.R;
import com.example.basepackagelib.tools.ScreenUtil;

public abstract class BaseDialogFragment extends DialogFragment implements OnClickListener{
	protected View parentRootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.custom_dialog);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentRootView=inflater.inflate(R.layout.base_dialog_frame, null);
		View contentView=onCreateView(inflater,savedInstanceState);
		FrameLayout contentContainer=(FrameLayout) parentRootView.findViewById(R.id.dialog_container);
		contentContainer.addView(contentView);
		parentRootView.findViewById(R.id.dialog_outside).setOnClickListener(this);
		return parentRootView;
	}
	
	/**
	 * 和父类的show()方法一样
	 * @param manager
	 * @param tag
	 * @param cancelable 点击对话框外围或者点击返回键，是否消失
	 */
	public void show(FragmentManager manager, String tag,boolean cancelable){
		super.show(manager, tag);
		if (cancelable) {
			parentRootView.findViewById(R.id.dialog_outside).setClickable(true);
			setCancelable(true);
		}else {
			parentRootView.findViewById(R.id.dialog_outside).setClickable(false);
			setCancelable(false);
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			if (window != null) {
				WindowManager.LayoutParams params = window.getAttributes();
				params.flags = LayoutParams.FLAG_DIM_BEHIND;
				params.width = ScreenUtil.getScreenWidth(getActivity());
				params.height = ScreenUtil.getScreenHeight(getActivity())
						- ScreenUtil.getStatusHeight();
				window.setAttributes(params);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.dialog_outside) {
			dismiss();
		} 
	}
	
	public abstract View onCreateView(LayoutInflater inflater,Bundle savedInstanceState);
	
}

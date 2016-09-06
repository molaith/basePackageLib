package com.example.basepackagelib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.basepackagelib.R;

public abstract class BaseBottomDialogFragment extends BaseDialogFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().setWindowAnimations(R.style.base_bottomDialogAnim);
		View rootview=inflater.inflate(R.layout.base_bottom_dialog_frame, null);
		View contentView=onCreateView(inflater,savedInstanceState);
		FrameLayout contentContainer=(FrameLayout) rootview.findViewById(R.id.dialog_container);
		contentContainer.addView(contentView);
		rootview.findViewById(R.id.dialog_outside).setOnClickListener(this);
		return rootview;
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.dialog_outside) {
			dismiss();
		} 
	}
	
}

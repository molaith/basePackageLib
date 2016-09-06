package com.example.basepackagelib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseTitleBar extends ViewGroup {
	private ImageView ivLeft;
	private TextView tvTitle;
	private LinearLayout layoutRight;
	private ImageView defRightDelete;
	private ImageView defRightAdd;
	
	private int paddingHorizon;
	private int paddingVertical;
	
	private Drawable iv_Left;
	
	private int title_textColor;
	private String titleText;
	private float titleTextSize;
	
	public BaseTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseTitleBar(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		paddingHorizon=15;
		paddingVertical=10;
		VectorDrawableCompat compat=(VectorDrawableCompat) ivLeft.getDrawable();
		setPadding(paddingHorizon, paddingVertical, paddingHorizon, paddingVertical);
		
		ivLeft=new ImageView(getContext());
		ivLeft.setAdjustViewBounds(true);
		ivLeft.setScaleType(ImageView.ScaleType.CENTER);
		tvTitle=new TextView(getContext());
		layoutRight=new LinearLayout(getContext());
		
		addView(ivLeft);
		addView(tvTitle);
		addView(layoutRight);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int ivLLeft=paddingHorizon;
		int ivLTop=paddingVertical;

		int icLeftH=iv_Left.getIntrinsicHeight();
		int icLeftW=iv_Left.getIntrinsicWidth();
		ivLeft.layout(ivLLeft, ivLTop,ivLLeft+icLeftW+20, ivLTop+icLeftH+20);


	}

}

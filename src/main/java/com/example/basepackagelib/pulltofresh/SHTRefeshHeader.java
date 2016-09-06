package com.example.basepackagelib.pulltofresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.basepackagelib.R;

public class SHTRefeshHeader extends LinearLayout implements PtrUIHandler {
	private PullListHeader progress;
	
	public SHTRefeshHeader(Context context) {
        super(context);
        initViews(null);
    }

    public SHTRefeshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public SHTRefeshHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }
    
    public void initViews(AttributeSet attrs){
    	View header = LayoutInflater.from(getContext()).inflate(R.layout.loading_layout, this);
    	progress=(PullListHeader) header.findViewById(R.id.progress);
    }

	@Override
	public void onUIReset(PtrFrameLayout frame) {
		progress.reSet();
	}

	@Override
	public void onUIRefreshPrepare(PtrFrameLayout frame) {
	}

	@Override
	public void onUIRefreshBegin(PtrFrameLayout frame) {
		progress.updateTex("刷呀刷呀^_^好累呀");
		progress.dealCircleAnim();
	}

	@Override
	public void onUIRefreshComplete(PtrFrameLayout frame) {
		progress.stopAnin();
	}

	@Override
	public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch,
			byte status, PtrIndicator ptrIndicator) {
		final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
		
        float percent=ptrIndicator.getCurrentPercent();
        
		if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
			if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
				progress.updateTex("你不松手怎么刷新呢(°□°；)");
			}
			
			progress.updateProgress(percent*0.5f);
		}
	}

}

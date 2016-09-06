package com.example.basepackagelib.pulltofresh;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.basepackagelib.tools.SizeFitUtil;

public class PullListHeader extends View {
	private Paint mPaint;
	private float percentage = 0;
	private String tips = "下拉刷新";

	private Paint txtPaint;
	private Rect rec = null;
	private RectF oval = null;

	private float angle = -77;
	private boolean isstop=false;

	public PullListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		mPaint.setColor(Color.GRAY);

		txtPaint = new Paint();
		txtPaint.setAntiAlias(true);
		txtPaint.setColor(Color.GRAY);
		txtPaint.setTextSize(25);

		rec = new Rect();
	}
	
	public void stopAnin(){
		isstop=true;
	}
	
	public void reSet(){
		angle=-77;
		isstop=false;
		tips = "下拉刷新";
		percentage = 0;
	}

	public void updateProgress(float percentage) {
		if (percentage > 0.9) {
			percentage = 0.9f;
		}
		this.percentage = percentage;
		invalidate();
	}

	public void updateTex(String tips) {
		this.tips = tips;
		invalidate();
	}

	public void dealCircleAnim() {
		if (isstop) {
			reSet();
			return;
		}
		ValueAnimator anim = ValueAnimator.ofFloat(360);
		anim.setDuration(400);
		anim.setInterpolator(new LinearInterpolator());
		anim.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				angle=-77+(Float) animation.getAnimatedValue();
				invalidate();
			}
		});
		anim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				percentage=0.9f;
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				dealCircleAnim();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		anim.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		txtPaint.getTextBounds(tips, 0, tips.length(), rec);

		if (oval == null) {
			int left = (getMeasuredWidth() / 2)
					- ((SizeFitUtil.dip2px(getContext(), 40) + rec.width()) / 2);
			oval = new RectF(left + 6, 6, left
					+ SizeFitUtil.dip2px(getContext(), 30) - 6,
					getMeasuredHeight() - 6);
		}
		canvas.drawArc(oval, angle, percentage * 360, false, mPaint);

		canvas.drawText(tips,
				oval.right + SizeFitUtil.dip2px(getContext(), 10),
				getMeasuredHeight() / 2 + rec.height() / 2, txtPaint);
	}

}

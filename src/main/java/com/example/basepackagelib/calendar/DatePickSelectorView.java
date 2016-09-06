package com.example.basepackagelib.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.basepackagelib.R;
import com.example.basepackagelib.tools.SizeFitUtil;

public class DatePickSelectorView extends View {
	private Paint bgPaint;
	private Paint textPaint;
	private int bgColor_checked;
	private int bgColor_normal = Color.TRANSPARENT;
	private int textColor;
	private int textColor_checked;
	private float textSize;
	private boolean isChecked = false;
	private String text;
	private boolean isclicable = true;
	private IOnCheckStateChangeListener listener;

	public DatePickSelectorView(Context context) {
		super(context);
		init();
	}

	public DatePickSelectorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(bgColor_normal);
		bgPaint.setStyle(Paint.Style.FILL);

		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);

		textColor = getResources().getColor(R.color.material_deep_teal_500);
		textColor_checked = Color.WHITE;
		textSize = SizeFitUtil.sp2px(getContext(), 12);
		bgColor_checked = getResources().getColor(R.color.material_deep_teal_500);
	}

	public void setChecked(boolean checkstate) {
		isChecked = checkstate;
		invalidate();
	}

	public boolean isChecked() {
		return isChecked;
	}
	
	public void setCheckedColor(int color){
		bgColor_checked=color;
		invalidate();
	}
	
	public void setText(CharSequence text) {
		this.text = text.toString();
		invalidate();
	}

	public void setTextColor(int color) {
		this.textColor = color;
		invalidate();
	}

	public void setTextSize(float size) {
		this.textSize = size;
		invalidate();
	}

	public void setTextSize(int typedValue, float size) {
		if (typedValue == TypedValue.COMPLEX_UNIT_SP) {
			this.textSize = SizeFitUtil.sp2px(getContext(), size);
		} else {
			this.textSize = size;
		}
		invalidate();
	}
	
	public String getText(){
		return text;
	}

	public void setCheckChangeListener(IOnCheckStateChangeListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int radius = ((getMeasuredWidth() / 2) < (getMeasuredHeight() / 2)) ? (getMeasuredWidth() / 2)
				: (getMeasuredHeight() / 2);
		if (isChecked) {
			bgPaint.setColor(bgColor_checked);
			textPaint.setColor(textColor_checked);
		} else {
			bgPaint.setColor(bgColor_normal);
			textPaint.setColor(textColor);
		}

		canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
				radius, bgPaint);

		textPaint.setTextSize(textSize);
		float textW = textPaint.measureText(text);
		float x = getMeasuredWidth() / 2 - textW / 2;
		float y = getMeasuredHeight() / 2 + textSize / 2;
		canvas.drawText(text, x, y, textPaint);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		if (l == null) {
			isclicable = false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!isclicable) {
				break;
			}
			isChecked = true;
			listener.onCheckChanged(isChecked, this);
			invalidate();
			break;
			
		case MotionEvent.ACTION_UP:
			if (!isclicable) {
				break;
			}
			invalidate();
			break;
		}
		return super.onTouchEvent(event);
	}

	public interface IOnCheckStateChangeListener {
		void onCheckChanged(boolean ischecked, DatePickSelectorView view);
	}

}

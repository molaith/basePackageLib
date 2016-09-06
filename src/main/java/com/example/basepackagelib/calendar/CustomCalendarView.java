package com.example.basepackagelib.calendar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.example.basepackagelib.R;

public class CustomCalendarView extends LinearLayout implements
		OnPageChangeListener {
	private ViewPager pager;
	private int currentYear;
	private int currentMonth;
	private int currentDay;

	private List<CalendarMonthFragment> months;
	private FragmentManager fm;

	private IDateCheckedListener listener;
	private int changedH;

	public CustomCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setBackgroundColor(Color.WHITE);
		setGravity(Gravity.CENTER);
		pager = new ViewPager(getContext());
		pager.setId(R.id.pager);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pager.setLayoutParams(lp);
		addView(pager);
	}

	public void setFragmentMagager(FragmentManager fm) {
		this.fm = fm;
	}

	private void initMonthPager() {
		if (months == null) {
			months = new ArrayList<CalendarMonthFragment>();
		} else {
			months.clear();
		}

		for (int i = 0; i < 3; i++) {
			CalendarMonthFragment month = new CalendarMonthFragment();
			int monthOfyear = currentMonth - (1 - i);
			int tempYear = currentYear;

			if (monthOfyear > 12) {
				monthOfyear = 1;
				tempYear = tempYear + 1;
			}

			if (currentMonth == monthOfyear) {
				month.setCurrentDayHighLight(monthOfyear);
			}

			setMonth(tempYear, monthOfyear, currentDay, i, month);
			months.add(month);
		}
		if (fm != null) {
			pager.setAdapter(new MonthPagerAdapter(fm));
			pager.setCurrentItem(1);
			pager.addOnPageChangeListener(this);
		}else throw new NullPointerException("FragmentManager must not be null");
	}

	private void updateCalendar() {
		for (int i = 0; i < 3; i++) {
			CalendarMonthFragment month = months.get(i);
			int monthOfyear = currentMonth - (1 - i);
			int tempYear = currentYear;

			month.initCalendar(tempYear, monthOfyear, currentDay, listener);
		}
	}

	public void initCalendar(int year, int month, int day,
			IDateCheckedListener listener) {
		this.currentYear = year;
		this.currentMonth = month + 1;
		this.currentDay = day;
		this.listener = listener;
		initMonthPager();
	}

	private void setMonth(int year, int month, int day, int position,
			CalendarMonthFragment fragment) {
		fragment.initCalendar(year, month, day, listener);
	}

	private class MonthPagerAdapter extends FragmentPagerAdapter {

		public MonthPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return months.get(arg0);
		}

		@Override
		public int getCount() {
			return months.size();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (h > 0) {
			changedH = 0;
			LinearLayout parent = (LinearLayout) pager.getChildAt(0);
			for (int i = 0; i < parent.getChildCount(); i++) {
				changedH = changedH + parent.getChildAt(i).getMeasuredHeight();
			}
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (changedH > 0) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(changedH,
					MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	private int targetPosition;

	@Override
	public void onPageSelected(int position) {
		targetPosition = position;
		if (position == 0) {
			currentMonth = currentMonth - 1;
			if (currentMonth < 1) {
				currentMonth = 12;
				currentYear = currentYear - 1;
			}
		} else if (position == months.size() - 1) {
			currentMonth = currentMonth + 1;
			if (currentMonth > 12) {
				currentMonth = 1;
				currentYear = currentYear + 1;
			}
		}
		targetPosition = 1;

		if (position != targetPosition) {
			updateCalendar();
			pager.postDelayed(new Runnable() {

				@Override
				public void run() {
					pager.getAdapter().notifyDataSetChanged();
					pager.setCurrentItem(targetPosition, false);
				}
			}, 50);
		}
	}

}

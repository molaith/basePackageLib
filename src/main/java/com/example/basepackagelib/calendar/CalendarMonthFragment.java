package com.example.basepackagelib.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.basepackagelib.R;
import com.example.basepackagelib.calendar.DatePickSelectorView.IOnCheckStateChangeListener;
import com.example.basepackagelib.tools.SizeFitUtil;

public class CalendarMonthFragment extends Fragment {
	private RecyclerView calendar;
	private TextView tv_year;
	private TextView tv_month;
	private View rootView;
	
	private int year;
	private int month;
	private int day;
	private int highlightMonth;
	
	private String[] numbers=new String[]{"0","1","2","3","4","5","6","7","8","9"};
	private List<String> dateInMonth;
	private int fistday_in_month_of_week;
	private int maxDayOFMonth;
	private boolean iscurrentHighLight=false;
	
	private IDateCheckedListener listener;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.layout_calendar, null);
		calendar=(RecyclerView) rootView.findViewById(R.id.calendar);
		tv_year=(TextView) rootView.findViewById(R.id.tv_year);
		tv_month=(TextView) rootView.findViewById(R.id.tv_month);
		initCalendar();
		
		return rootView;
	}
	
	public int getMonth(){
		return month;
	}
	
	public void initCalendar(int year, int month, int day,IDateCheckedListener listener){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DATE, 1);
		fistday_in_month_of_week=c.get(Calendar.DAY_OF_WEEK)-1;
		c.set(Calendar.DATE, day);
		maxDayOFMonth=c.getMaximum(Calendar.DAY_OF_MONTH);
		this.year=year;
		this.month=month;
		this.day=day;
		this.listener=listener;
		
		if (calendar!=null) {
			initCalendar();
		}
	}
	
	private void initCalendar(){
		if (dateInMonth==null) {
			dateInMonth=new ArrayList<String>();
		}else {
			dateInMonth.clear();
		}
		
		dateInMonth.add("日");
		dateInMonth.add("一");
		dateInMonth.add("二");
		dateInMonth.add("三");
		dateInMonth.add("四");
		dateInMonth.add("五");
		dateInMonth.add("六");
		
		int notThisMonthSize=fistday_in_month_of_week;
		for (int i = 0; i < notThisMonthSize; i++) {
			dateInMonth.add("");
		}
		
		for (int i = 1; i <= maxDayOFMonth; i++) {
			dateInMonth.add(""+i);
		}
		
		calendar.setLayoutManager(new GridLayoutManager(getContext(), 7,
				GridLayoutManager.VERTICAL, false));
		calendar.setAdapter(new CalendarAdapter());
		calendar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, SizeFitUtil.dip2px(getActivity(), 280)));
		
		tv_year.setText(year+"年");
		tv_month.setText(month+"月");
	}
	
	public void setCurrentDayHighLight(int month){
		highlightMonth=month;
		iscurrentHighLight=true;
	}
	
	private class CalendarAdapter extends Adapter<CalendarAdapter.DateHolder>{
		class DateHolder extends ViewHolder{
			DatePickSelectorView text;

			public DateHolder(View arg0) {
				super(arg0);
				text=(DatePickSelectorView) arg0;
			}
			
		}

		@Override
		public int getItemCount() {
			return dateInMonth.size();
		}
		
		@Override
		public void onBindViewHolder(DateHolder holder, int position) {
			String date=dateInMonth.get(position);
			holder.text.setText(date);
			boolean isRealdate=false;
			for (int i = 0; i < numbers.length; i++) {
				if (date.contains(numbers[i])) {
					isRealdate=true;
					break;
				}
			}
			if (isRealdate) {
				holder.text.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					}
				});
			}else {
				holder.text.setOnClickListener(null);
			}
			
			if (iscurrentHighLight&&date.equals(String.valueOf(day))&&highlightMonth==month) {
				holder.text.setTextColor(Color.RED);
				holder.text.setChecked(true);
			}
			
			
			holder.text.setCheckChangeListener(new IOnCheckStateChangeListener() {
				
				@Override
				public void onCheckChanged(boolean ischecked, DatePickSelectorView view) {
					for (int i = 0; i < calendar.getChildCount(); i++) {
						DatePickSelectorView child=(DatePickSelectorView) calendar.getChildAt(i);
//						if (iscurrentHighLight) {
//							if (!view.equals(child)&&(!view.getText().equals(String.valueOf(day)))) {
//								child.setChecked(false);
//							}
//						}else {
							if (!view.equals(child)) {
								child.setChecked(false);
							}
//						}
					}
					listener.onDateChecked(year, month, Integer.parseInt(view.getText()));
				}
			});
		}

		@Override
		public DateHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			DatePickSelectorView dateText=new DatePickSelectorView(getContext());
			dateText.setLayoutParams(new LayoutParams(SizeFitUtil.dip2px(getContext(), 40), SizeFitUtil.dip2px(getContext(), 40)));
			DateHolder holder=new DateHolder(dateText);
			return holder;
		}
	}
}

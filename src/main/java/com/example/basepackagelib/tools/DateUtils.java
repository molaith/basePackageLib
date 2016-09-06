package com.example.basepackagelib.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.res.Resources;
import android.text.TextUtils;

/**
 * 日期相关的操作 工具类
 * 
 * @author ddc
 * @date: Jul 4, 2014 10:34:59 PM
 */
public class DateUtils {

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/**
	 * yyyy-MM-dd
	 */
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * HH:mm
	 */
	public static final SimpleDateFormat DATE_FORMAT_HHMM = new SimpleDateFormat(
			"HH:mm");
	/**
	 * yyyyMMdd
	 */
	public static final SimpleDateFormat DATE_FORMAT_YYMMDD = new SimpleDateFormat(
			"yyyyMMdd");
	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年

	/**
	 * 评论时间
	 * 
	 * @param times
	 * @return
	 */
	public static String getCommTimeStr(long times) {
		Calendar curCal = Calendar.getInstance(Locale.CHINA);
		Calendar reviewCal = Calendar.getInstance(Locale.CHINA);
		reviewCal.setTimeInMillis(times);

		int curYear = curCal.get(Calendar.YEAR);
		int curMonth = curCal.get(Calendar.MONTH);
		int curDay = curCal.get(Calendar.DAY_OF_MONTH);

		int reviewYear = reviewCal.get(Calendar.YEAR);
		int reviewMonth = reviewCal.get(Calendar.MONTH);
		int reviewDay = reviewCal.get(Calendar.DAY_OF_MONTH);

		if (curYear == reviewYear && curMonth == reviewMonth
				&& curDay == reviewDay) {
			return "今天";
		}
		if (curYear == reviewYear) {
			return formatdate_MMddHHmm(times);
		}
		return formatdateDot(times);
	}
	
	/**
	 * 
	 * @return 下单时获取配送日期
	 */
	public static String getOrderDate(){
		Calendar calendar=Calendar.getInstance();
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		String date=DateUtils.getString(System.currentTimeMillis(), "yyyy-MM-dd");
		int dayofweek=calendar.getTime().getDay();
		if (dayofweek>=5) {
			calendar.add(Calendar.DATE, 7-dayofweek+1);
		}else if (dayofweek==4) {
			if (hour<18) {
				calendar.add(Calendar.DATE, 1);
			}else {
				calendar.add(Calendar.DATE, 7-dayofweek+1);
			}
		}else {
			if (hour<18) {
				calendar.add(Calendar.DATE, 1);
			}else {
				calendar.add(Calendar.DATE, 2);
			}
		}
		date=getFormatedDate(calendar.getTime(), "yyyy-MM-dd");
		return date;
	}

	/**
	 * 获取已记录天数
	 * 
	 * @param context
	 * @param currentMillis
	 * @return
	 */
//	public static int getRecordedDay(Context context, long currentMillis) {
//		int tianshu = 1;
//		DataPerefence df = new DataPerefence(context);
//		if (df.getFirstDate() > 0) {
//			String current = DateUtils.getString(currentMillis, "yyyy年MM月dd");
//			String first = DateUtils.getString(df.getFirstDate(), "yyyy年MM月dd");
//
//			int yearC = Integer.parseInt(current.split("年")[0]);
//			int monthC = Integer.parseInt(current.split("年")[1].split("月")[0]);
//			int dayC = Integer.parseInt(current.split("年")[1].split("月")[1]);
//
//			int yearF = Integer.parseInt(first.split("年")[0]);
//			int monthF = Integer.parseInt(first.split("年")[1].split("月")[0]);
//			int dayF = Integer.parseInt(first.split("年")[1].split("月")[1]);
//
//			Date currentDate = new Date(yearC, monthC, 0);
//			Date firstDate = new Date(yearF, monthF, 0);
//
//			if (monthC != monthF) {
//				tianshu = firstDate.getDate() - dayF + dayC+1;
//			} else {
//				tianshu = dayC - dayF+1;
//			}
//		}
//		return tianshu;
//	}

	public static int getMonthDays(int year, int month) {
		Date date = new Date(year, month, 0);
		return date.getDate();
	}

	/**
	 * 根据一个Date对象返回格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatedDate(Date date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日",
				Locale.getDefault());
		try {
			return sDateFormat.format(date);
		} catch (Exception e) {

		}
		return date.toString();
	}
	
	/**
	 * 
	 * 根据一个Date对象返回格式化时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatedDate(Date date,String format) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format,
				Locale.getDefault());
		try {
			return sDateFormat.format(date);
		} catch (Exception e) {

		}
		return date.toString();
	} 

	/**
	 * 返回文字描述的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}

	public static String formatOrderUpDayTime(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日HH时",
				Locale.getDefault());
		try {
			return sDateFormat.format(Long.valueOf(time));
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * yyyy-MM-dd 字符串日期转换为 MM月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMMDD(String date) {
		return formatOrderUpDay(convertDate2long(date, "yyyy-MM-dd"));
	}

	/**
	 * yyyy-MM-dd 字符串日期转换为 MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMM_DD(String date) {
		return formatMM_DD(convertDate2long(date, "yyyy-MM-dd"));
	}

	/**
	 * long 型日期期转换为 MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static String formatMM_DD(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd",
				Locale.getDefault());
		try {
			return sDateFormat.format(Long.valueOf(time));
		} catch (Exception e) {
		}
		return "";
	}

	public static String formatOrderUpDay(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日",
				Locale.getDefault());
		try {
			return sDateFormat.format(Long.valueOf(time));
		} catch (Exception e) {
		}
		return "";
	}

	public static String formatOrderUpTime(long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH时",
				Locale.getDefault());
		try {
			return sDateFormat.format(Long.valueOf(time));
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 格式化时间未字符串
	 * 
	 * @param format
	 *            格式：如 yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String formatTime(String format, long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format,
				Locale.getDefault());
		try {
			return sDateFormat.format(Long.valueOf(time));
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String formatdate(String currenttime) {
		try {
			long curtime = Long.parseLong(currenttime);
			return formatdate(curtime);
		} catch (Exception e) {
			// return StringUtils.getNonNullString(currenttime);
			return currenttime;
		}
	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss:SSS
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdateSSS(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return sdf.format(date);
	}

	/**
	 * 格式化日期HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdateHHMM(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化日期HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdateHHMM(String currenttime) {
		try {
			long curtime = Long.parseLong(currenttime);
			Date date = new Date(curtime);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 格式化日期yyyy-MM-dd
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_yyyyMMdd(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String formatdate_yyyyMMdd(String currenttime) {
		try {
			long curtime = Long.parseLong(currenttime);
			return formatdate_yyyyMMdd(curtime);
		} catch (Exception e) {
			// return StringUtils.getNonNullString(currenttime);
			return "";
		}
	}

	/**
	 * 格式化日期MM/dd
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_Md(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("M/d");
		return sdf.format(date);
	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_yyyyMMddHHmm(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化日期yyyy.MM.dd HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdateDot(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化日期dd/MM hh:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_MMdd_hhmm(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化日期dd/MM hh:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_MMdd_hhmm(String currenttime) {
		long l = System.currentTimeMillis();
		try {
			l = Long.parseLong(currenttime);
		} catch (Exception e) {
		}
		return formatdate_MMdd_hhmm(l);
	}

	/**
	 * 格式化日期dd_MM hh:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_MM_dd_hhmm(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
		return sdf.format(date);
	}

	public static String formatdate_MM_dd_hhmm(Double currenttime) {
		String result = currenttime + "";
		Date date = new Date(result);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化 日期 dd HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_ddhhmm(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化 日期 MM.dd HH:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_MMddHHmm(long currenttime) {
		Date date = new Date(currenttime);
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 格式化日期dd_MM hh:mm
	 * 
	 * @param currenttime
	 * @return
	 */
	public static String formatdate_MM_dd_hhmm(String currenttime) {
		long l = System.currentTimeMillis();
		try {
			l = Long.parseLong(currenttime);
		} catch (Exception e) {
		}
		return formatdate_MM_dd_hhmm(l);
	}

	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convertDate2long(String date, String format) {
		try {
			if (!TextUtils.isEmpty(date)) {
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	public static String getDayStr(String date) {
		long l = convertDate2long(date, "yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
		return getWeek(date) + "  " + sdf.format(l);
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convertDate2String(long time, String format) {
		if (time > 0l) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);

			return sf.format(date);
		}
		return "";
	}

	/**
	 * 将以秒为单位的时间转换为时分秒格式
	 * 
	 * @param s
	 * @return
	 */
	public static String formatSecondTime(Resources res, int s) {
		int hour = s / 3600;
		int minute = (s % 3600) / 60;
		// int second = (s % 3600) % 60;
		String time = "";
		if (hour > 0) {
			time += hour + "小时";
		}
		if (minute > 0) {
			time += minute + "分钟";
		}
		// time += second + res.getString(R.string.second);
		return time;
	}

	/**
	 * 将以秒为单位的时间转换为天时分格式
	 * 
	 * @param s
	 * @return
	 */
	public static String formatSecondTime(float s) {
		int s1 = (int) s;
		int day = s1 / (24 * 60 * 60);
		int hour = (s1 % (24 * 60 * 60)) / (60 * 60);
		int minute = (s1 % (60 * 60)) / 60;
		String time = "";
		if (day > 0) {
			time += day + "d";
		}
		if (hour > 0) {
			time += hour + "h";
		}
		time += minute + "m";
		return time;
	}

	/**
	 * 更具 字符串日期 2012-04-26，得到周几
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getWeekShort(String strDate) {
		String[] weekArray = new String[] { "", "周日", "周一", "周二", "周三", "周四",
				"周五", "周六" };
		try {
			Date date = DateFormat.getDateInstance(DateFormat.SHORT,
					Locale.CHINESE).parse(strDate);
			Calendar calendar = Calendar.getInstance(Locale.CHINESE);
			calendar.setTime(date);

			// SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY.
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			return weekArray[week];
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 更具 字符串日期 2012-04-26，得到周几
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getWeek(String strDate) {
		String[] weekArray = new String[] { "", "星期日", "星期一", "星期二", "星期三",
				"星期四", "星期五", "星期六" };
		try {
			Date date = DateFormat.getDateInstance(DateFormat.SHORT,
					Locale.CHINESE).parse(strDate);
			Calendar calendar = Calendar.getInstance(Locale.CHINESE);
			calendar.setTime(date);

			// SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY.
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			return weekArray[week];
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取周几
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getWeekStr(Calendar calendar) {
		String str = "";

		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY: {
			str = "Mon";
			break;
		}
		case Calendar.TUESDAY: {
			str = "Tue";
			break;
		}
		case Calendar.WEDNESDAY: {
			str = "Wed";
			break;
		}
		case Calendar.THURSDAY: {
			str = "Thu";
			break;
		}
		case Calendar.FRIDAY: {
			str = "Fri";
			break;
		}
		case Calendar.SATURDAY: {
			str = "Sat";
			break;
		}
		case Calendar.SUNDAY: {
			str = "Sun";
			break;
		}
		}
		return str;
	}

	/**
	 * 格式化 时间 串
	 * 
	 * @param s
	 * @return
	 */
	public static String formatDateTime(long s) {
		Calendar curCal = Calendar.getInstance();

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(curCal.getTimeInMillis());

		if (curCal.get(Calendar.DATE) == cal.get(Calendar.DATE)) {
			return formatdateHHMM(cal.getTimeInMillis());
		}
		return formatdate_yyyyMMddHHmm(cal.getTimeInMillis());
	}

	/**
	 * 获取标准时间值（毫秒）
	 * 
	 * @param datevalue
	 *            时间值
	 * @param datefoemat
	 *            时间格式
	 * @return
	 */
	public static long getLong(String datevalue, String datefoemat) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(datefoemat);
		java.util.Date mydate = null;
		try {
			mydate = myFormatter.parse(datevalue);
		} catch (Exception e) {
		}
		long day = (mydate == null ? 0 : mydate.getTime());
		return day;
	}

	/**
	 * 获取指定格式时间
	 * 
	 * @param datevalue
	 *            时间值
	 * @param datefoemat
	 *            时间格式
	 * @return
	 */
	public static String getString(long datevalue, String datefoemat) {
		SimpleDateFormat sdf = new SimpleDateFormat(datefoemat);
		String formatedDate = sdf.format(datevalue);
		return formatedDate;
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	/**
	 * 获取当前时间：HH:mm
	 * 
	 * @return
	 */
	public static String getCurrentTimeInHhMm() {
		return getTime(getCurrentTimeInLong(), DATE_FORMAT_HHMM);
	}

	// ==================================================================
	/** 早上 **/
	public static final int TIME_PERIOD_MORNING = 1000;
	/** 中午 **/
	public static final int TIME_PERIOD_NOON = 1001;
	/** 下午 **/
	public static final int TIME_PERIOD_AFTERNOON = 1002;
	/** 晚上 **/
	public static final int TIME_PERIOD_NIGHT = 1003;

	/**
	 * 获取时间段:一天分四个时段，6~11：早上，11~14：中午，14~17：下午，17~6：晚上
	 * 
	 * @return
	 */
	public static int getTimePeriod() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour < 6) {
			return TIME_PERIOD_NIGHT;
		}
		if (hour < 11) {
			return TIME_PERIOD_MORNING;
		}
		if (hour < 14) {
			return TIME_PERIOD_NOON;
		}
		if (hour < 17) {
			return TIME_PERIOD_AFTERNOON;
		}
		return TIME_PERIOD_NIGHT;
	}

	public static String formatdateyyyyMMdd(long currenttime) {
		Date date = new Date(currenttime);
		return DATE_FORMAT_YYMMDD.format(date);
	}
}

package com.example.basepackagelib.tools;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

/**
 * 获取当前手机设备的相关信息 工具类
 *
 * @Create Date: 2012-10-24
 */
public class AppUtils {

	public static void sendSms(Context context,String message,String phone){
		if (CheckUtil.isEmpty(message)){
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phone));
			intent.putExtra("sms_body", message);
			context.startActivity(intent);
		}else {
			//获取短信管理器
			android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
			//拆分短信内容（手机短信长度限制）
			List<String> divideContents = smsManager.divideMessage(message);
			for (String text : divideContents) {
				smsManager.sendTextMessage(phone, null, text, null, null);
			}
		}
	}

	/**
	 * 拨号
	 *
	 * @param context
	 * @param mobile
	 */
	public static void call(Context context, String mobile) {
		// 叫出拨号程序
		// Uri uri = Uri.parse("tel:" + mobile);
		// Intent intent = new Intent(Intent.ACTION_DIAL, uri);
		// context.startActivity(intent);

		// 直接打电话出去 需要添加打电话权限：
		// <uses-permission android:name="android.permission.CALL_PHONE" />
		Uri uri = Uri.parse("tel:" + mobile);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		context.startActivity(intent);
	}

	/**
	 * 隐藏键盘
	 *
	 * @param context
	 */
	public static void hideSoftInput(Context context, IBinder token) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 判断是否安装对应程序
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 判断是否有对应的intent
	 *
	 * @param context
	 * @param intent
	 * @return
	 */
	public static boolean isIntentCallable(Context context, Intent intent) {
		List<ResolveInfo> list = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * 获取IMSI号
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		if (TextUtils.isEmpty(imsi)) {
			imsi = "000000000000000";
		}
		return imsi;
	}

	/**
	 * 获取 IMEI 号
	 *
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String IMEI = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return IMEI;
	}

	public static int getSDKVer() {
		return Build.VERSION.SDK_INT;
	}

	public static float getBuildVersion() {
		return Float.parseFloat(Build.VERSION.RELEASE);
	}

	public static String getDeviceModel() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (TextUtils.isEmpty(s)) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	/**
	 * 获取电话号码
	 *
	 * @param context
	 * @return
	 */
	public static final String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getLine1Number();
		if (number == null) {
			return "";
		}
		return number;
	}


	/**
	 * 获取ActionBar 高度
	 * http://stackoverflow.com/questions/12301510/how-to-get-the-
	 * actionbar-height
	 *
	 * @param context
	 * @return
	 */
	public static int getActionBarHeight(Context context) {
		int actionBarHeight = 0;
		// TypedValue tv = new TypedValue();
		// if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
		// tv, true)) {
		// actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
		// context.getResources().getDisplayMetrics());
		// }
		// if (actionBarHeight == 0
		// && context.getTheme().resolveAttribute(
		// com.actionbarsherlock.R.attr.actionBarSize, tv, true)) {
		// actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
		// context.getResources().getDisplayMetrics());
		// }

		// TypedValue tv = new TypedValue();
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		// if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
		// tv, true))
		// actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
		// context
		// .getResources().getDisplayMetrics());
		// } else if
		// (context.getTheme().resolveAttribute(com.actionbarsherlock.R.attr.actionBarSize,
		// tv, true)) {
		// actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
		// context
		// .getResources().getDisplayMetrics());
		// }
		return actionBarHeight;
	}

	/**
	 * 获取软件当前的版本号-
	 *
	 * @param context
	 * @return
	 */
	public static String getSoftver(Context context) {
		String ver = "0";
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			String version = info.versionName;
			@SuppressWarnings("unused")
			String versioncode = String.format(Locale.CHINA, "%03d",
					info.versionCode);
			// 是否需要 versionName 和 versionCode 两个值组合
			ver = version /* + "." + versioncode */;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ver;
	}

	public static int getSoftvercode(Context context) {
		int vercode = 0;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			vercode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return vercode;
	}
}

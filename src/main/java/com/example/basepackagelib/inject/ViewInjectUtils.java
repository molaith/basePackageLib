package com.example.basepackagelib.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class ViewInjectUtils {
	private static final String METHOD_SET_CONTENTVIEW = "setContentView";
	private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

	public static void inject(Activity activity) {
		injectContentView(activity);
		injectViews(activity);
	}

	/**
	 * 
	 * 注入主布局
	 * 
	 * @param activity
	 */
	private static void injectContentView(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		// 查询类上是否存在ContentView注解
		ContentView contentView = clazz.getAnnotation(ContentView.class);
		if (contentView != null)// 存在
		{
			int contentViewLayoutId = contentView.value();
			try {
				Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW,
						int.class);
				method.setAccessible(true);
				method.invoke(activity, contentViewLayoutId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 注入所有的控件
	 * 
	 * @param activity
	 */
	private static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 遍历所有成员变量
		for (Field field : fields) {

			ViewInject viewInjectAnnotation = field
					.getAnnotation(ViewInject.class);
			if (viewInjectAnnotation != null) {
				int viewId = viewInjectAnnotation.value();
				if (viewId != -1) {
					Log.e("TAG", viewId + "");
					// 初始化View
					try {
						Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID,
								int.class);
						Object resView = method.invoke(activity, viewId);
						field.setAccessible(true);
						field.set(activity, resView);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}
	}

	public static void injectFragmentViews(Fragment fragment, View rootView) {
		Class<? extends Fragment> clazz = fragment.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 遍历所有成员变量
		for (Field field : fields) {

			ViewInject viewInjectAnnotation = field
					.getAnnotation(ViewInject.class);
			if (viewInjectAnnotation != null) {
				int viewId = viewInjectAnnotation.value();
				if (viewId != -1) {
					Log.e("TAG", viewId + "");
					// 初始化View
					try {
						Method method = rootView.getClass().getMethod(
								METHOD_FIND_VIEW_BY_ID, int.class);
						Object resView = method.invoke(rootView, viewId);
						field.setAccessible(true);
						field.set(fragment, resView);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}
	}
}

package com.example.basepackagelib.tools;

import java.math.BigDecimal;

/**
 * 数字处理相关工具
 * 
 * @author molaith
 * 
 */
public class NumberUtil {
	
	/**
	 * 
	 * @param size
	 * @return 将字节数格式化
	 */
	public static String[] getIntRealSize(long value){
		String unit = "M";
		StringBuilder size = new StringBuilder();
		float f = (float) value;
		if (value > 1023) {
			f = div(f, 1024, 1);
			if (f > 1023) {
				f = div(f, 1024, 1);
				if (f > 999) {
					f = div(f, 1024, 1);
					size.append(f);
					unit = "G";
				} else {
					size.append(f);
					unit = "M";
				}
			} else {
				size.append(f);
				unit = "K";
			}
		} else if (value > 0) {
			size.append(value);
			unit = "B";
		} else {
			size.append('0');
		}
		return new String[] {size.toString(), unit};
	}

	/**
	 * 两个数的除法，并精确到小数点后面scale位
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 *            精度
	 * @return
	 */
	public static float div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (float) b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 将字节数格式化，精确到小数点后一位
	 * 
	 * @param size
	 * @return
	 */
	public static String getRealSize(long size) {
		String realSize = size + "B";
		float fsize = div(size, 1024, 1);
		if (fsize > 0) {
			if (fsize < 1024) {
				realSize = fsize + "K";
			} else if (fsize >= 1024 && fsize < (1024 * 1024)) {
				if (fsize>=(1000*1024)) {
					realSize=(div(size, 1024 * 1024 * 1024, 1))+"G";
				}else {
					realSize = (div(size, 1048576, 1)) + "M";
				}
			} else if (fsize >= (1024 * 1024)) {
				realSize = (div(size, (1024 * 1024 * 1024), 1)) + "G";
			}
		}
		return realSize;
	}
//	大于100M就转换成G
	public static String getSurpOrTot(long size) {
		String realSize = size + "B";
		float fsize = div(size, 1024, 1);
		if (fsize > 0) {
			if (fsize < 1024) {
				realSize = fsize + "K";
			} else if (fsize >= 1024 && fsize < (1024 * 1024)) {
				if (fsize>=(100*1024)) {
					realSize=(div(size, 1024 * 1024 * 1024, 1))+"G";
				}else {
					realSize = (div(size, 1048576, 1)) + "M";
				}
			} else if (fsize >= (1024 * 1024)) {
				realSize = (div(size, (1024 * 1024 * 1024), 1)) + "G";
			}
		}
		return realSize;
	}
	public static String getFormatSize(long value) {
		StringBuilder size = new StringBuilder();
		float f = (float) value;
		if (value > 1023) {
			f = div(f, 1024, 1);
			if (f > 1023) {
				f = div(f, 1024, 1);
				if (f > 1023) {
					f = div(f, 1024, 1);
					size.append(f).append("G");
				} else {
					size.append(f).append("M");
				}
			} else {
				size.append(f).append("K");
			}
		} else {
			size.append(value).append('B');
		}
		return size.toString();
	}
	
	public static String[] getFormatValues(long value) {
		String unit = "M";
		StringBuilder size = new StringBuilder();
		float f = (float) value;
		if (value > 1023) {
			f = div(f, 1024, 1);
			if (f > 1023) {
				f = div(f, 1024, 1);
				if (f > 1023) {
					f = div(f, 1024, 1);
					size.append(f);
					unit = "G";
				} else {
					size.append(f);
					unit = "M";
				}
			} else {
				size.append(f);
				unit = "K";
			}
		} else if (value > 0) {
			size.append(value);
			unit = "B";
		} else {
			size.append('0');
		}
		return new String[] {size.toString(), unit};
	}
}

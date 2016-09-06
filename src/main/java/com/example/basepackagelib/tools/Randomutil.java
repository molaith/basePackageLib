package com.example.basepackagelib.tools;

import java.util.Random;

import android.graphics.Color;

public class Randomutil {
	
	/**
	 * 生成随机数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNum(int min,int max){
		return (min+(int)(Math.random()*(max-min+1)));
	}
	
	/**
	 * 
	 * 生成随机颜色
	 * @return
	 */
	public static int getRandomColor(){
		String premix = "#";
		String R = Integer.toHexString(new Random().nextInt(255));
		if (R.length() < 2) {
			R = "0" + R;
		}
		String G = Integer.toHexString(new Random().nextInt(255));
		if (G.length() < 2) {
			G = "0" + G;
		}
		String B = Integer.toHexString(new Random().nextInt(255));
		if (B.length() < 2) {
			B = "0" + B;
		}
		return Color.parseColor(premix + R + G + B);
	}

}

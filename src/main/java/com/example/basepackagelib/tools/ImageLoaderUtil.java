package com.example.basepackagelib.tools;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.example.basepackagelib.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderUtil {

	public static void init(Context context) {
		String dir=context.getResources().getString(R.string.app_name);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true)
				.showImageOnFail(R.drawable.bg_img_default).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.diskCache(
						new UnlimitedDiscCache(new File(Environment
								.getExternalStorageDirectory()
								+ File.separator
								+ dir
								+ File.separator
								+ "imgCach")))
				.defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置
				build();
		ImageLoader.getInstance().init(config); // 初始化
	}
	
	/**
	 * 
	 * @param uri 网络图片 "http://site.com/image.png";
	 *      <p>
	 *      本地图片 "file:///mnt/sdcard/image.png";
	 *      <p>
	 *      相册图片 "content://media/external/audio/albumart/13";
	 *      <p>
	 *      assets图片 "assets://image.png";
	 *      <p>
	 *      drawble图片 "drawable://" + R.drawable.image; .9图不行
	 *      <p>
	 * 
	 * @param imageView
	 */
	public static void showImage(String uri,ImageView imageView){
		ImageLoader.getInstance().displayImage(uri, imageView);
	}

}

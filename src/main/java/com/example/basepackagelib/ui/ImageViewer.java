package com.example.basepackagelib.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.basepackagelib.R;
import com.example.basepackagelib.base.BaseDialogFragment;
import com.example.basepackagelib.tools.ImageLoaderUtil;
import com.example.basepackagelib.tools.SizeFitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by molaith on 2016/7/28.
 */
public class ImageViewer extends BaseDialogFragment implements ViewPager.OnPageChangeListener{
    private ViewPager pager;
    private LinearLayout dotContainer;

    private List<String> urls;
    private List<ImageView> imgs;
    private int current=0;

    @Override
    public View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_image_viewer,null);
        pager= (ViewPager) rootView.findViewById(R.id.pager);
        dotContainer= (LinearLayout) rootView.findViewById(R.id.layout_container);
        parentRootView.findViewById(R.id.dialog_container).setBackgroundColor(Color.TRANSPARENT);
        initImages();
        return rootView;
    }

    public void setUrls( List<String> urls){
        this.urls=urls;
    }

    public void setCurrentPosition(int position){
        current=position;
    }

    private void initImages(){
        if (urls!=null&&urls.size()>0){
            imgs=new ArrayList<>();
            for (String url:urls){
                ImageView image=new ImageView(getActivity());
                image.setOnClickListener(this);
                image.setAdjustViewBounds(true);
                ViewPager.LayoutParams layoutParams=new ViewPager.LayoutParams();
                layoutParams.width=ViewPager.LayoutParams.MATCH_PARENT;
                layoutParams.height=ViewPager.LayoutParams.MATCH_PARENT;
                image.setLayoutParams(layoutParams);
                ImageLoaderUtil.showImage(url,image);
                imgs.add(image);
            }
            pager.setAdapter(new ImagesAdapter());
            pager.setOnPageChangeListener(this);
            initPoint();
            pager.setCurrentItem(current);
        }else {

            Toast.makeText(getActivity(),"图片为空!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initPoint() {
        if (dotContainer.getChildCount() > 0) {
            dotContainer.removeAllViews();
        }
        for (int i = 0; i < urls.size(); i++) {
            ImageView point = new ImageView(getActivity());
            int size=SizeFitUtil.dip2px(getActivity(), 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);
            if (i > 0) {
                params.setMargins(size, 0, 0,0);
            }
            point.setLayoutParams(params);
            point.setImageResource(R.drawable.dot1_w);
            dotContainer.addView(point);
        }
        freshPoint();
    }

    private void freshPoint() {
        for (int i = 0; i < urls.size(); i++) {
            ImageView point = (ImageView) dotContainer.getChildAt(i);
            if (i == pager.getCurrentItem()) {
                point.setImageResource(R.drawable.dot2_w);
            } else {
                point.setImageResource(R.drawable.dot1_w);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        freshPoint();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ImagesAdapter extends PagerAdapter{

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgs.get(position));
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView child=imgs.get(position);
            container.addView(child);
            return  child;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
    }
}

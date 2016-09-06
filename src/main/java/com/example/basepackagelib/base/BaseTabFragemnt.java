package com.example.basepackagelib.base;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.basepackagelib.R;
import com.example.basepackagelib.tools.SizeFitUtil;

/**
 * Created by molaith on 2016/7/19.
 */
public abstract class BaseTabFragemnt extends Fragment implements ViewPager.OnPageChangeListener{
    protected String[] tabTexts;
    private OnTabChangedListener listener;
    private Drawable tabSelectedBG;
    private  View rootView;
    protected ViewPager pager;

    public abstract View onCreateView(LayoutInflater inflater, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_base_tab, null);
        FrameLayout frame = (FrameLayout) rootView.findViewById(R.id.layout_container);
        View frameContent = onCreateView(inflater, savedInstanceState);
        if (frameContent != null) {
            frame.addView(frameContent);
            if (pager!=null){
                pager.addOnPageChangeListener(this);
            }
        } else {

        }
        initTab();
        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (pager!=null){
            setTab(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void initTab(){
        LinearLayout tabGroup=(LinearLayout) rootView.findViewById(R.id.tab_container);
        if (tabTexts!=null&&tabTexts.length>0){
            for (int i=0;i<tabTexts.length;i++) {
                TextView tab=new TextView(getActivity());
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, SizeFitUtil.dip2px(getActivity(),45),1f);
                tab.setGravity(Gravity.CENTER);
                tab.setText(tabTexts[i]);
                if (tabSelectedBG==null&&i==0) {
                    tab.setBackgroundResource(R.drawable.bg_tab_default);
                }
                tab.setOnClickListener(new OnTabClickListener(i));
                tabGroup.addView(tab,layoutParams);
            }
        }
    }

    public void setTabsText(String[] tabs){
        this.tabTexts=tabs;
    }

    public void setOnTabChangeListener(OnTabChangedListener listener){
        this.listener=listener;
    }

    public interface OnTabChangedListener{
        public void onTabChecked(int position,View tab);
    }

    private void setTab(int position){
        LinearLayout tabGroup=(LinearLayout) rootView.findViewById(R.id.tab_container);
        for (int i=0;i<tabGroup.getChildCount();i++){
            View child=tabGroup.getChildAt(i);
            if (position==i){
                child.setBackgroundResource(R.drawable.bg_tab_default);
            }else {
                child.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private class OnTabClickListener implements View.OnClickListener{
        private int position;

        public OnTabClickListener(int position){
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            setTab(position);
            if (pager!=null){
                pager.setCurrentItem(position);
            }

            if (listener!=null){
                listener.onTabChecked(position,v);
            }
        }
    }

}

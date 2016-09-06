package com.example.basepackagelib.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.basepackagelib.R;
import com.example.basepackagelib.widget.BaseTitleBar;

public class BaseToolBarDrawerActivity extends AppCompatActivity {
	private boolean isTollBarInside=false;

	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.toolbar_with_drawer_layout);
		initToolBar();
	}

	@Override
	public void setContentView( int layoutResID) {
		if (isTollBarInside) {
			super.setContentView(R.layout.toobar_inside_drawer_layout);
		}else {
			super.setContentView(R.layout.toolbar_with_drawer_layout);
		}

		ViewGroup parent = (ViewGroup) findViewById(R.id.layout_content);
		View convertView = LayoutInflater.from(this).inflate(layoutResID,
				parent, false);

		parent.addView(convertView);

		initToolBar();
	}
	
	/**
	 * set toolbar above the drawer or below the drawer.</br>
	 * call before setContentView() called
	 * @param isInside default false
	 */
	public void setToolBarInside(boolean isInside){
		this.isTollBarInside=isInside;
	}
	
	/**
	 * set drawer menu
	 * @param menu
	 */
	public void setMenu(Fragment menu){
		 getSupportFragmentManager().beginTransaction().add(R.id.left_menu_container, menu).commit();
	}
	
	@Override
	public void onBackPressed() {
		if (isMenuOpen()) {
			closeLeftMenu();
		}else {
			super.onBackPressed();
		}
	}
	
	public void closeLeftMenu(){
		DrawerLayout drawer=(DrawerLayout) findViewById(R.id.id_drawerlayout);
		drawer.closeDrawer(Gravity.LEFT);
	}
	
	public boolean isMenuOpen(){
		DrawerLayout drawer=(DrawerLayout) findViewById(R.id.id_drawerlayout);
		return drawer.isDrawerOpen(Gravity.LEFT);
	}

	private void initToolBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
		// toolbar.setLogo(R.mipmap.ic_launcher);
		// Title
		toolbar.setTitle(getString(R.string.app_name));
		toolbar.setTitleTextColor(Color.WHITE);
		// Sub Title

		setSupportActionBar(toolbar);
		// Navigation Icon
//		toolbar.setNavigationIcon(R.drawable.ic_menu_drawer);

		initViews(toolbar);
	}

	/**
	 * 
	 * @param title
	 * @param color if you don't want to change color pass -1
	 */
	public void setToolBarTitle(String title,int color) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
		toolbar.setTitle(title);
		if (color!=-1) {
			toolbar.setTitleTextColor(color);
		}

	}

	/**
	 * 
	 * @param title
	 * @param color if you don't want to change color pass -1
	 */
	public void setToolBarSubTitle(String title,int color) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
		toolbar.setSubtitle(title);
	}

	private void initViews(Toolbar mToolbar) {
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);

		ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
				this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
		mActionBarDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
	}

	public Toolbar getToolBar() {
		return (Toolbar) findViewById(R.id.id_toolbar);
	}
}

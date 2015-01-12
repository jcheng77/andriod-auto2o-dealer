package com.cettco.buycar.dealer.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.fragment.MyCarFragment;
import com.cettco.buycar.dealer.fragment.SettingsFragment;
import com.cettco.buycar.dealer.fragment.WelcomeFragment;
import com.cettco.buycar.dealer.utils.UpdateManager;
import com.cettco.buycar.dealer.utils.UserUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SlidingMenu menu;
	private TextView currentCitytTextView;
	private LinearLayout settingLayout;
	private ImageButton drawerButton;
	private LinearLayout cityLayout;
	private TextView titleTextView;

	private LinearLayout welcomelLayout;
	//private ImageButton scanImageBtn;
	private LinearLayout scanLly;
	private LinearLayout drawerLly;

	// private LinearLayout logoutLayout;

	// private ImageView launchingImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setMyTitle("Welcome");
		setContentView(R.layout.activity_main);
		// getActionBar().hide();
		// launchingImageView =
		// (ImageView)findViewById(R.id.launching_imageview);
		titleTextView = (TextView) findViewById(R.id.welcome_actionbar_title_textview);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);

		switchFragment(new WelcomeFragment());

		// logoutLayout = (LinearLayout)findViewById(R.id.logoutLayout);
		// logoutLayout.setOnClickListener(logoutClickListener);

		welcomelLayout = (LinearLayout) findViewById(R.id.welcome_linearLayout);
		welcomelLayout.setOnClickListener(welcomeClickListener);

		settingLayout = (LinearLayout) findViewById(R.id.settingLinearlayout);
		settingLayout.setOnClickListener(settingsClickListener);

//		drawerButton = (ImageButton) findViewById(R.id.actionbar_drawer);
//		drawerButton.setOnClickListener(drawerClickListener);
		
		drawerLly = (LinearLayout)findViewById(R.id.main_drawer_lly);
		drawerLly.setOnClickListener(drawerClickListener);

		cityLayout = (LinearLayout) findViewById(R.id.selectCityLinearLayout);
		cityLayout.setOnClickListener(selectCityClickListener);

		/*
		 * addImageButton = (ImageButton) findViewById(R.id.addCar_btn);
		 * addImageButton.setOnClickListener(addCarClickListener);
		 */
		//scanImageBtn = (ImageButton) findViewById(R.id.welcome_actionbar_scan_imagebtn);
		//scanImageBtn.setOnClickListener(scanClickListener);
		scanLly = (LinearLayout)findViewById(R.id.welcome_actionbar_scan_lly);
		scanLly.setOnClickListener(scanClickListener);

		currentCitytTextView = (TextView) findViewById(R.id.menuCurrentCityTextView);

		// Launching view
		// launchingImageView.setVisibility(View.GONE);
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		switch (selection) {
		case 0:
			currentCitytTextView.setText("上海");
			break;
		case 1:
			currentCitytTextView.setText("北京");
			break;
		default:
			break;
		}
		SharedPreferences updatePreferences = getSharedPreferences("update", 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault());
		Date date = new Date();
		String date_now = format.format(date);
		String lasted_update = updatePreferences.getString("time", "19700101");
		if (!date_now.equals(lasted_update)) {
			UpdateManager manager = new UpdateManager(this);
			// 检查软件更新
			manager.checkUpdate();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		switch (selection) {
		case 0:
			currentCitytTextView.setText("上海");
			break;
		case 1:
			currentCitytTextView.setText("北京");
			break;
		default:
			break;
		}
	}

	private OnClickListener scanClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(MainActivity.this)) {
				Toast toast = Toast.makeText(MainActivity.this, "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
			startActivity(intent);
		}
	};
	private OnClickListener accountClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, SignInActivity.class);
			// startActivity(intent);
			// if(UserUtil.isLogin(MainActivity.this)){
			// switchFragment(new MyCarFragment());
			// setMyTitle("My car");
			// logoutLayout.setVisibility(View.VISIBLE);
			// addImageButton.setVisibility(View.GONE);
			// menu.toggle();
			// }
			// else {
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, SignInActivity.class);
			// startActivity(intent);
			// }
			switchFragment(new MyCarFragment());
			setMyTitle("我的车");
			// logoutLayout.setVisibility(View.VISIBLE);
			// addImageButton.setVisibility(View.GONE);
			menu.toggle();
		}

	};
	protected OnClickListener welcomeClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// logoutLayout.setVisibility(View.GONE);
			// addImageButton.setVisibility(View.VISIBLE);
			switchFragment(new WelcomeFragment());
			setMyTitle("首页");
			menu.toggle();
		}
	};
	protected OnClickListener addCarClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent();
			// //intent.setClass(MainActivity.this, CarListActivity.class);
			// startActivity(intent);

		}
	};

	protected OnClickListener selectCityClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SelectCityActivity.class);
			// ArrayList<String> myArrayList = new ArrayList<String>();
			// myArrayList.add("Beijin");
			// myArrayList.add("Shanghai");
			// myArrayList.add("Guangzhou");
			// intent.putStringArrayListExtra("list", myArrayList);
			startActivity(intent);
		}
	};
	protected OnClickListener drawerClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			menu.toggle();

		}
	};
	protected OnClickListener settingsClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switchFragment(new SettingsFragment());
			setMyTitle("设置");
			// menu.
			// System.out.println(menu.isActivated());
			menu.toggle();
			// System.out.println(menu.isActivated());
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, OrderHasDealerActivity.class);
			// startActivity(intent);
		}
	};

	protected void setMyTitle(String title) {
		titleTextView.setText(title);
	}

	protected void switchFragment(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	public void exitClick(View view) {
		finish();
	}
}

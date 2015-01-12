package com.cettco.buycar.dealer.activity;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cettco.buycar.dealer.activity.IntroductionActivity;
import com.cettco.buycar.dealer.activity.MainActivity;
import com.cettco.buycar.dealer.activity.SplashActivity;
import com.cettco.buycar.dealer.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,BaiduPushUtils.getMetaValue(SplashActivity.this, "api_key"));
		SharedPreferences settings = getSharedPreferences("installed", 0);
		if (settings.getBoolean("first", true)) {
			int versionCode = getVersionCode(SplashActivity.this);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("first", false);
			editor.putInt("version", versionCode);
			editor.commit();
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, IntroductionActivity.class);
			startActivity(intent);
			this.finish();
		} else {
			int versionCode = getVersionCode(SplashActivity.this);
			if (versionCode == settings.getInt("version", -1)) {
				Handler h = new Handler();
				h.postDelayed(new Runnable() {
					public void run() {
						Intent intent = new Intent();
						intent.setClass(SplashActivity.this, MainActivity.class);
						startActivity(intent);
						SplashActivity.this.finish();
					}

				}, 2000);
			} else {
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("first", false);
				editor.putInt("version", versionCode);
				editor.commit();
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, IntroductionActivity.class);
				startActivity(intent);
				this.finish();
			}
		}
		
	}
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.cettco.buycar", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

}

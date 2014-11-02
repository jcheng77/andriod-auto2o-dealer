package com.cettco.buycar.dealer.utils;

import com.baidu.frontia.FrontiaApplication;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;

public class MyApplication extends FrontiaApplication{
	public static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	//public static CookieStore myCookieStore = new PersistentCookieStore(getac);
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//baseUrl="http://baidu.com/";
		super.onCreate();
	}
}

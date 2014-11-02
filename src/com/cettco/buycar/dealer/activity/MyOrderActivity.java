package com.cettco.buycar.dealer.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.adapter.MyOrderAdapter;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity{
	private ListView listView;
	//private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private List<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
	private ProgressBar progressBar;
	//private LinearLayout mycarBgLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		progressBar = (ProgressBar)findViewById(R.id.my_order_progressbar);
		listView = (ListView)findViewById(R.id.orders_listview);
		listView.setOnItemClickListener(itemClickListener);
//		for (int i = 0; i < 5; i++) {
//			OrderItemEntity entity = new OrderItemEntity();
//			list.add(entity);
//		}
		adapter = new MyOrderAdapter(this, R.layout.item_my_order,
				list);
		listView.setAdapter(adapter);
		//adapter.updateList(list);
		getData();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int position = arg2;
			OrderItemEntity orderItemEntity = list.get(position);
			String state = orderItemEntity.getState();
			System.out.println("order:"+state);
			Intent intent = new Intent();
			intent.setClass(MyOrderActivity.this,
					OrderDetailActivity.class);
			intent.putExtra("bargain_id", list.get(position).getBargain_id());
			startActivity(intent);

		}
	};
	protected void getData() {
		progressBar.setProgress(40);
		String url=GlobalData.getBaseUrl()+"/tenders/dealer_index.json";
		Gson gson = new Gson();
		StringEntity entity = null;
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		if (myCookieStore == null) {
			System.out.println("cookie store null");
			return;
		}
		List<Cookie> cookies = myCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			cookieName = name;
			System.out.println(name);
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				System.out.println("value:" + cookieStr);
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
			System.out.println("cookie null");
			return;
		}
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		//HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url,new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				progressBar.setProgress(60);
				try {
					String result= new String(arg2,"UTF-8");
					System.out.println("result:"+result);
					Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
					}.getType();
					list = new Gson().fromJson(result, listType);
					//System.out.println("size:"+dealerList.size());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					//System.out.println("result:"+result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				adapter.updateList(list);
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
				break;
			case 2:
				Toast toast = Toast.makeText(MyOrderActivity.this, "获取订单失败",
						Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};
	public void exitClick(View view){
		this.finish();
	}
	
}

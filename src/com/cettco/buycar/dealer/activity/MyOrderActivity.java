package com.cettco.buycar.dealer.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.adapter.MyOrderAdapter;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.PersistentCookieStore;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity{
	private ListView listView;
	private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private List<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
	//private LinearLayout mycarBgLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		//getActionBar().hide();
		//mycarBgLayout = (LinearLayout)findViewById(R.id.orders_bg_layout);
		pullToRefreshView = (PullToRefreshListView)findViewById(R.id.orders_pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}

				});
		listView = pullToRefreshView.getRefreshableView();
		listView.setOnItemClickListener(itemClickListener);
		for (int i = 0; i < 5; i++) {
			OrderItemEntity entity = new OrderItemEntity();
			list.add(entity);
		}
		adapter = new MyOrderAdapter(this, R.layout.item_my_order,
				list);
		listView.setAdapter(adapter);
		adapter.updateList(list);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("onResume");
		//pullToRefreshView.setRefreshing(true);

	}
	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int position = arg2 - 1;
			OrderItemEntity orderItemEntity = list.get(position);
			String state = orderItemEntity.getState();
			System.out.println("order:"+state);
			Intent intent = new Intent();
			intent.setClass(MyOrderActivity.this,
					OrderDetailActivity.class);
			startActivity(intent);
//			if (state.equals("viewed")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						CarDetailActivity.class);
//				intent.putExtra("order_id", orderItemEntity.getOrder_id());
//				startActivity(intent);
//			} else if (state.equals("begain")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						BargainActivity.class);
//				intent.putExtra("order_id", orderItemEntity.getOrder_id());
//				startActivity(intent);
//
//			} else if (state.equals("determined")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						AliPayActivity.class);
//				startActivity(intent);
//
//			} else if (state.equals("qualified")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						OrderWaitingActivity.class);
//				startActivity(intent);
//
//			} else if (state.equals("timeout")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						MyOrderStatusActivity.class);
//				startActivity(intent);
//
//			} else if (state.equals("sumbitted")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						OrderHasDealerActivity.class);
//				startActivity(intent);
//			} else if (state.equals("deal_made")) {
//				Intent intent = new Intent();
//				intent.setClass(MyOrderActivity.this,
//						OrderHasDealerActivity.class);
//				intent.putExtra("id", orderItemEntity.getId());
//				startActivity(intent);
//			}

		}
	};
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			getData();
			return null;
		}
	}
	private void getData() {
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(MyOrderActivity.this);
		List<Cookie> cookies = myCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			cookieName = name;
			if (name.equals("_JustBidIt_session")) {
				cookieStr = cookie.getValue();
				break;
			}
		}
		if (cookieStr == null || cookieStr.equals("")) {
			return;
		}
		HttpClient httpclient = new DefaultHttpClient();
		String uri = GlobalData.getBaseUrl() + "/tenders.json";
		HttpGet get = new HttpGet(uri);
		// 添加http头信息
		get.addHeader("Cookie", cookieName + "=" + cookieStr);
		get.addHeader("Content-Type", "application/json");
		org.apache.http.HttpResponse response;
		try {
			response = httpclient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String result = EntityUtils.toString(response.getEntity());
				System.out.println("result:"+result);
				Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
				}.getType();
				list = new Gson().fromJson(result, listType);
				Message message = new Message();
				message.what = 1;
				mHandler.sendMessage(message);
			} else if (code == 401) {
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				//adapter.updateList(list);
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

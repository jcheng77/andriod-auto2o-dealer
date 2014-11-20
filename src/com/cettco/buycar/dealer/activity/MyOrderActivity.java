package com.cettco.buycar.dealer.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.adapter.MyOrderAdapter;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity {
	private PullToRefreshListView pullToRefreshListView;
	// private PullToRefreshListView pullToRefreshView;
	private MyOrderAdapter adapter;
	private List<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
	private int global_page=1;
	
	private TextView titleTextView;

	// private LinearLayout mycarBgLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("订单列表");
		pullToRefreshListView = (PullToRefreshListView)findViewById(R.id.orders_pull_to_refresh_listview);
		pullToRefreshListView.setMode(Mode.BOTH);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>(){  
			   
            // 下拉Pulling Down  
            @Override  
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 下拉的时候数据重置  
            	new GetDataTask(1).execute();
            }  
              
            // 上拉Pulling Up  
            @Override  
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {  
                // 上拉的时候添加选项   
            	new GetDataTask(global_page+1).execute();
            }  
   
        });
		ListView listView = pullToRefreshListView.getRefreshableView();
		listView.setOnItemClickListener(itemClickListener);
		adapter = new MyOrderAdapter(this, R.layout.item_my_order, list);
		listView.setAdapter(adapter);
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private int page;
		public GetDataTask(int page){
	            this.page = page;
	        }
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			getData(page);
			return null;
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pullToRefreshListView.setRefreshing(true);

	}

	protected OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println("position:"+arg2);
			if(list==null||list.size()==0)return;
			int position = arg2-1;
			OrderItemEntity orderItemEntity = list.get(position);
			String state = orderItemEntity.getState();
			String bider = orderItemEntity.getBider();
			if (state.equals("qualified")) {
				Intent intent = new Intent();
				intent.setClass(MyOrderActivity.this, OrderDetailActivity.class);
				intent.putExtra("bargain_id", list.get(position)
						.getBargain_id());
				intent.putExtra("id", list.get(position).getId());
				intent.putExtra("bid_id", list.get(position).getBid_id());
				startActivity(intent);
			} else if (state.equals("taken") && bider.equals("you")) {
				Intent intent = new Intent();
				intent.setClass(MyOrderActivity.this, OrderDetailActivity.class);
				intent.putExtra("bargain_id", list.get(position)
						.getBargain_id());
				intent.putExtra("id", list.get(position).getId());
				intent.putExtra("bid_id", list.get(position).getBid_id());
				startActivity(intent);
			} else if (state.equals("deal_made") && bider.equals("you")) {
				Intent intent = new Intent();
				intent.setClass(MyOrderActivity.this, OrderDetailActivity.class);
				intent.putExtra("bargain_id", list.get(position)
						.getBargain_id());
				intent.putExtra("id", list.get(position).getId());
				intent.putExtra("bid_id", list.get(position).getBid_id());
				startActivity(intent);
			}

		}
	};
	
	private void getData(int page) {
		String cookieStr = null;
		String cookieName = null;
		PersistentCookieStore myCookieStore = new PersistentCookieStore(
				MyOrderActivity.this);
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
		String url = GlobalData.getBaseUrl() + "/tenders/dealer_index.json";
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("page",String.valueOf(page));
		String uri = builder.build().toString();
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
				List<OrderItemEntity> tmpEntities = new Gson().fromJson(result, listType);
				if(tmpEntities!=null){
					if(page==1){
						list = tmpEntities;
					}else{
						global_page=global_page+1;
						list.addAll(tmpEntities);
					}
					
				}
				
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

//	protected void getData() {
//		progressBar.setProgress(40);
//		String url = GlobalData.getBaseUrl() + "/tenders/dealer_index.json";
//		Gson gson = new Gson();
//		StringEntity entity = null;
//		String cookieStr = null;
//		String cookieName = null;
//		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
//		if (myCookieStore == null) {
//			System.out.println("cookie store null");
//			return;
//		}
//		List<Cookie> cookies = myCookieStore.getCookies();
//		for (Cookie cookie : cookies) {
//			String name = cookie.getName();
//			cookieName = name;
//			System.out.println(name);
//			if (name.equals("_JustBidIt_session")) {
//				cookieStr = cookie.getValue();
//				System.out.println("value:" + cookieStr);
//				break;
//			}
//		}
//		if (cookieStr == null || cookieStr.equals("")) {
//			System.out.println("cookie null");
//			return;
//		}
//		HttpConnection.getClient().addHeader("Cookie",
//				cookieName + "=" + cookieStr);
//		// HttpConnection.setCookie(getApplicationContext());
//		HttpConnection.get(url, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//					Throwable arg3) {
//				// TODO Auto-generated method stub
//				Message message = new Message();
//				message.what = 2;
//				mHandler.sendMessage(message);
//			}
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				progressBar.setProgress(60);
//				try {
//					String result = new String(arg2, "UTF-8");
//					System.out.println("result:" + result);
//					Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
//					}.getType();
//					list = new Gson().fromJson(result, listType);
//					// System.out.println("size:"+dealerList.size());
//					Message message = new Message();
//					message.what = 1;
//					mHandler.sendMessage(message);
//					// System.out.println("result:"+result);
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		});
//	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				adapter.updateList(list);
				break;
			case 2:
				Toast toast = Toast.makeText(MyOrderActivity.this, "获取订单失败",
						Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		};
	};

	public void exitClick(View view) {
		this.finish();
	}

}

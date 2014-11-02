package com.cettco.buycar.dealer.activity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.widget.ProgressBar;
>>>>>>> c209d4f

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class OrderDetailActivity extends Activity {
	private Button submitButton;
	private Button accpetButton;
<<<<<<< HEAD
	private TextView stateTextView;
=======
	private String bargain_id;
	private ProgressBar progressBar;
>>>>>>> c209d4f

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		submitButton = (Button)findViewById(R.id.order_detail_submit_btn);
		submitButton.setOnClickListener(submitBtnClickListener);
		accpetButton = (Button)findViewById(R.id.order_detail_accept_btn);
		accpetButton.setOnClickListener(acceptBtnClickListener);
<<<<<<< HEAD
		
		stateTextView = (TextView)findViewById(R.id.order_detail_state_layout);
=======
		bargain_id = getIntent().getStringExtra("bargain_id");
		progressBar = (ProgressBar)findViewById(R.id.order_detail_progressbar);
		getData();
>>>>>>> c209d4f
	}

	protected OnClickListener acceptBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	protected OnClickListener submitBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(OrderDetailActivity.this,
					MipcaActivityCapture.class);
			startActivity(intent);
		}
	};
	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url = GlobalData.getBaseUrl() + "/tenders/"+bargain_id+"/show_bargain.json";
		System.out.println("url:"+url);
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
		progressBar.setProgress(40);
		HttpConnection.get(url,new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				progressBar.setProgress(60);
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
				System.out.println("fail");
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				progressBar.setProgress(60);
				progressBar.setProgress(100);
				progressBar.setVisibility(View.GONE);
				try {
					String result= new String(arg2,"UTF-8");
					System.out.println("result2:"+result);
//					Type listType = new TypeToken<ArrayList<OrderItemEntity>>() {
//					}.getType();
//					list = new Gson().fromJson(result, listType);
//					//System.out.println("size:"+dealerList.size());
//					Message message = new Message();
//					message.what = 1;
//					mHandler.sendMessage(message);
					//System.out.println("result:"+result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	private void updateUI(){
		String state="";
		if(state.equals("111")){
			stateTextView.setText("待接受订单");
			stateTextView.setBackgroundColor(Color.parseColor("#FF6600"));
			submitButton.setVisibility(View.GONE);
			accpetButton.setVisibility(View.VISIBLE);
		}else if(state.equals("111")){
			stateTextView.setText("待提交详细信息");
			stateTextView.setBackgroundColor(Color.parseColor("#FF6600"));
			submitButton.setVisibility(View.VISIBLE);
			accpetButton.setVisibility(View.GONE);
		}
		else if(state.equals("111")){
			stateTextView.setText("接受订单成功");
			stateTextView.setBackgroundColor(Color.parseColor("#FF6600"));
			submitButton.setVisibility(View.GONE);
			accpetButton.setVisibility(View.GONE);
		}
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// updateTitle();
				// adapter.notifyDataSetChanged();
				break;
			case 2:
				// Toast toast = Toast.makeText(getActivity(), "获取订单失败",
				// Toast.LENGTH_SHORT);
				// toast.show();
				break;
			}
		};
	};

	public void exitClick(View view) {
		this.finish();
	}

}

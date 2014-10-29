package com.cettco.buycar.dealer.activity;

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
import android.widget.TextView;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class OrderDetailActivity extends Activity {
	private Button submitButton;
	private Button accpetButton;
	private TextView stateTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		submitButton = (Button)findViewById(R.id.order_detail_submit_btn);
		submitButton.setOnClickListener(submitBtnClickListener);
		accpetButton = (Button)findViewById(R.id.order_detail_accept_btn);
		accpetButton.setOnClickListener(acceptBtnClickListener);
		
		stateTextView = (TextView)findViewById(R.id.order_detail_state_layout);
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
	private void getData() {
		String url = GlobalData.getBaseUrl() + "/tenders/"+"1"+".json";
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
		HttpConnection.get(url, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				// progressLayout.setVisibility(View.GONE);
				System.out.println("error");
				System.out.println("statusCode:" + statusCode);
				System.out.println("headers:" + headers);
				for (int i = 0; i < headers.length; i++) {
					System.out.println(headers[i]);
				}
				System.out.println("response:" + errorResponse);
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				System.out.println("success");
				System.out.println("statusCode:" + statusCode);
				System.out.println("headers:" + headers);
				System.out.println("response:" + response);
				try {
					String state=response.getString("state");
					String[] stateArray = getResources().getStringArray(R.array.state);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// progressLayout.setVisibility(View.GONE);
				// UserUtil.login(SignInActivity.this);

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

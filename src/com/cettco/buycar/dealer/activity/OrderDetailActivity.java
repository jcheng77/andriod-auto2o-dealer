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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.entity.OrderDetailEntity;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.cettco.buycar.dealer.utils.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class OrderDetailActivity extends Activity {
	private Button submitButton;
	private Button accpetButton;
	private TextView stateTextView;
	private String bargain_id;
	private String id;
	private String bid_id;
	private ProgressBar progressBar;
	private RelativeLayout progressLayout;
	private OrderDetailEntity detailEntity;
	private LinearLayout stateLayout;
	private LinearLayout bidLayout;
	
	private EditText insurancEditText;
	//private EditText vechileTaxEditText;
	private EditText purchaseEditText;
	private EditText licenseFeEditText;
	private EditText miscFeEditText;
	private EditText priceEditText;
	private EditText descriptionEditText;
	
	private TextView colorTextView;
	private TextView pickupTimeTextView;
	private TextView locationTextView;
	private TextView gotLicenseTextView;
	private TextView loanOptionTextView;
	private ImageView carImageView;
	private TextView brandMakerModelTextView;
	private TextView trimTextView;

	private TextView titleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		titleTextView = (TextView)findViewById(R.id.title_text);
		titleTextView.setText("订单详情");
		submitButton = (Button) findViewById(R.id.order_detail_submit_btn);
		submitButton.setOnClickListener(submitBtnClickListener);
		accpetButton = (Button) findViewById(R.id.order_detail_accept_btn);
		accpetButton.setOnClickListener(acceptBtnClickListener);

		stateTextView = (TextView) findViewById(R.id.order_detail_status_textview);
		bargain_id = getIntent().getStringExtra("bargain_id");
		id = getIntent().getStringExtra("id");
		bid_id = getIntent().getStringExtra("bid_id");
		progressBar = (ProgressBar) findViewById(R.id.order_detail_progressbar);
		progressLayout = (RelativeLayout) findViewById(R.id.progressbar_relativeLayout);
		stateLayout = (LinearLayout) findViewById(R.id.order_detail_state_layout);
		bidLayout = (LinearLayout)findViewById(R.id.bids_layout);
		bidLayout.setVisibility(View.GONE);
		
		insurancEditText =(EditText)findViewById(R.id.module_bid_insurance_edittext);
		//vechileTaxEditText = (EditText)findViewById(R.id.module_bid_vehicle_tax_edittext);
		purchaseEditText = (EditText)findViewById(R.id.module_bid_purchase_tax_edittext);
		licenseFeEditText = (EditText)findViewById(R.id.module_bid_license_fee_edittext);
		miscFeEditText = (EditText)findViewById(R.id.module_bid_mis_fee_edittext);
		priceEditText = (EditText)findViewById(R.id.module_bid_price_edittext);
		descriptionEditText = (EditText)findViewById(R.id.module_bid_description_edittext);
		
		//stateLayout.setVisibility(View.GONE);
		
		colorTextView = (TextView)findViewById(R.id.order_detail_color_textview);
		pickupTimeTextView=(TextView)findViewById(R.id.order_detail_pickup_time_textview);
		locationTextView=(TextView)findViewById(R.id.order_detail_license_location_textview);
		gotLicenseTextView=(TextView)findViewById(R.id.order_detail_got_license_textview);
		loanOptionTextView=(TextView)findViewById(R.id.order_detail_loan_option_textview);
		brandMakerModelTextView=(TextView)findViewById(R.id.order_detail_brandmakermodel_textview);
		trimTextView=(TextView)findViewById(R.id.order_detail_trim_textview);
		carImageView = (ImageView)findViewById(R.id.order_detail_car_img);
		getData();
	}

	protected OnClickListener acceptBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = GlobalData.getBaseUrl() + "/bids.json";
			accept(url);

		}
	};
	protected OnClickListener submitBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = GlobalData.getBaseUrl() + "/bids/"+bid_id+".json";
			submit(url);
		}
	};

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url = GlobalData.getBaseUrl() + "/tenders/" + id
				+ "/show_bargain.json";
		System.out.println("url:" + url);
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
		// HttpConnection.setCookie(getApplicationContext());
		progressBar.setProgress(40);
		HttpConnection.get(url, new AsyncHttpResponseHandler() {

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
					String result = new String(arg2, "UTF-8");
					System.out.println("result2:" + result);
					// Type listType = new
					// TypeToken<ArrayList<OrderItemEntity>>() {
					// }.getType();
					// list = new Gson().fromJson(result, listType);
					// //System.out.println("size:"+dealerList.size());
					detailEntity = new Gson().fromJson(result,
							OrderDetailEntity.class);
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private void submit(String url) {
		System.out.println("url:" + url);
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
		//String insurance = 
		String insurance = insurancEditText.getText().toString();
		//String vehicleTax = vechileTaxEditText.getText().toString();
		String purchaseTax = purchaseEditText.getText().toString();
		String licenseFee = licenseFeEditText.getText().toString();
		String miscFee = miscFeEditText.getText().toString();
		String price = priceEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		if(insurance.equals("")||purchaseTax.equals("")||licenseFee.equals("")||miscFee.equals("")||price.equals("")||description.equals("")){
			Toast toast = Toast.makeText(this, "有未填写数据", Toast.LENGTH_SHORT);
			toast.show();
		}
		JSONObject bidJson = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			bidJson.put("insurance", insurance);
			//bidJson.put("vehicle_tax", vehicleTax);
			bidJson.put("purchase_tax", purchaseTax);
			bidJson.put("license_fee", licenseFee);
			bidJson.put("misc_fee", miscFee);
			bidJson.put("price", price);
			bidJson.put("description", description);
			json.put("bid",bidJson);
			json.put("_method","patch");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Gson gson = new Gson();
		StringEntity entity = null;
		try {
			System.out.println("patch:"+json.toString());
			entity = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpConnection.getClient().addHeader("x-http-method-override","PATCH");
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		// HttpConnection.setCookie(getApplicationContext());
		progressLayout.setVisibility(View.VISIBLE);
		HttpConnection.post(this, url, null, entity,  "application/json;charset=utf-8",new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				progressLayout.setVisibility(View.GONE);
				System.out.println("fail");
				Message message = new Message();
				message.what = 5;
				mHandler.sendMessage(message);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				progressLayout.setVisibility(View.GONE);
				try {
					String result = new String(arg2, "UTF-8");
					System.out.println("order detail result:" + result);
					// Type listType = new
					// TypeToken<ArrayList<OrderItemEntity>>() {
					// }.getType();
					// list = new Gson().fromJson(result, listType);
					// //System.out.println("size:"+dealerList.size());
					Message message = new Message();
					message.what = 6;
					mHandler.sendMessage(message);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private void accept(String url) {
		System.out.println("url:" + url);
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
		JSONObject bidJson = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			bidJson.put("bargain_id", bargain_id);
			json.put("bid", bidJson);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Gson gson = new Gson();
		StringEntity entity = null;
		try {
			entity = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpConnection.getClient().addHeader("Cookie",
				cookieName + "=" + cookieStr);
		// HttpConnection.setCookie(getApplicationContext());
		progressLayout.setVisibility(View.VISIBLE);
		HttpConnection.post(OrderDetailActivity.this, url, null, entity,
				"application/json;charset=utf-8",
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						progressLayout.setVisibility(View.GONE);
						System.out.println("fail");
						Message message = new Message();
						message.what = 3;
						mHandler.sendMessage(message);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						progressLayout.setVisibility(View.GONE);
						try {
							String result = new String(arg2, "UTF-8");
							System.out.println("order detail submit:" + result);
							// Type listType = new
							// TypeToken<ArrayList<OrderItemEntity>>() {
							// }.getType();
							// list = new Gson().fromJson(result, listType);
							// //System.out.println("size:"+dealerList.size());
							Message message = new Message();
							message.what = 4;
							mHandler.sendMessage(message);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
	}

	private void updateUI() {
		MyApplication.IMAGE_CACHE.get(detailEntity.getPic_url(),carImageView);
		String[] name_array = detailEntity.getModel().split(" : ");
		// System.out.println(name_array);
		if (name_array.length == 5) {
			brandMakerModelTextView.setText(name_array[0] + " "+ name_array[1] + " " + name_array[2]);
			trimTextView.setText(name_array[3]);
			colorTextView.setText(name_array[4]);
		}
		pickupTimeTextView.setText(detailEntity.getPickup_time());
		locationTextView.setText(detailEntity.getLicense_location());
		if(detailEntity.getGot_licence().equals("0")){
			gotLicenseTextView.setText("无");
		}else if(detailEntity.getGot_licence().equals("1")){
			gotLicenseTextView.setText("有");
		}
		
		if(detailEntity.getLoan_option().equals("0")){
			loanOptionTextView.setText("只选择贷款");
		}else if(detailEntity.getLoan_option().equals("1")){
			loanOptionTextView.setText(" 全款");
		}else if(detailEntity.getLoan_option().equals("2")){
			loanOptionTextView.setText("贷款或全款均可");
		}
		
		String state = detailEntity.getState();
		// qualified,timeout,submitted,deal_made,final_deal_closed
		System.out.println("state:"+state);
		if (state.equals("qualified")) {
			stateTextView.setText("待接受订单");
			// stateTextView.setBackgroundColor(Color.parseColor("#FF6600"));
			submitButton.setVisibility(View.GONE);
			accpetButton.setVisibility(View.VISIBLE);
			bidLayout.setVisibility(View.GONE);
		} else if (state.equals("taken")) {
			stateTextView.setText("待提交详细信息");
			submitButton.setVisibility(View.VISIBLE);
			accpetButton.setVisibility(View.GONE);
			bidLayout.setVisibility(View.VISIBLE);
		} else if (state.equals("deal_made")) {
			stateTextView.setText("接受订单成功");
			submitButton.setVisibility(View.GONE);
			accpetButton.setVisibility(View.GONE);
			bidLayout.setVisibility(View.GONE);
		}
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				updateUI();
				break;
			case 2:
				Toast toast = Toast.makeText(OrderDetailActivity.this,
						"获取订单详情失败", Toast.LENGTH_SHORT);
				toast.show();
				break;
			case 3:
				Toast toast3 = Toast.makeText(OrderDetailActivity.this, "抢单失败",
						Toast.LENGTH_SHORT);
				toast3.show();
				break;
			case 4:
				Toast toast4 = Toast.makeText(OrderDetailActivity.this, "抢单成功",
						Toast.LENGTH_SHORT);
				toast4.show();
				accpetButton.setVisibility(View.GONE);
				submitButton.setVisibility(View.GONE);
				getData();
				break;
			case 5:
				Toast toast5 = Toast.makeText(OrderDetailActivity.this, "提交详情失败",
						Toast.LENGTH_SHORT);
				toast5.show();
				break;
			case 6:
				Toast toast6 = Toast.makeText(OrderDetailActivity.this, "提交详情成功",
						Toast.LENGTH_SHORT);
				toast6.show();
				accpetButton.setVisibility(View.GONE);
				submitButton.setVisibility(View.GONE);
				getData();
				break;
			}
		};
	};

	public void exitClick(View view) {
		this.finish();
	}

}

package com.cettco.buycar.dealer.fragment;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.activity.MainActivity;
import com.cettco.buycar.dealer.activity.SignInActivity;
import com.cettco.buycar.dealer.entity.User;
import com.cettco.buycar.dealer.entity.UserEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.cettco.buycar.dealer.utils.UpdateManager;
import com.cettco.buycar.dealer.utils.UserUtil;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private RelativeLayout loginLayout;
	private TextView loginTextView;
	private View fragmentView;
	private LinearLayout logoutLayout;
	private Button logouButton;
	private ImageView loginArrowImageView;
	//private RelativeLayout progressLayout;
	private RelativeLayout checkUpdateLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		fragmentView = inflater.inflate(R.layout.fragment_settings, container,
				false);
//		progressLayout = (RelativeLayout) fragmentView
//				.findViewById(R.id.progressbar_relativeLayout);
		loginArrowImageView = (ImageView)fragmentView.findViewById(R.id.settings_login_arrow_imageview);
		loginLayout = (RelativeLayout) fragmentView
				.findViewById(R.id.settings_login_layout);
		loginLayout.setOnClickListener(loginClickListener);
		loginTextView = (TextView) fragmentView
				.findViewById(R.id.settings_login_textView);

		logoutLayout = (LinearLayout) fragmentView
				.findViewById(R.id.logout_layout);
		logouButton = (Button) fragmentView.findViewById(R.id.logout_button);
		logouButton.setOnClickListener(logoutClickListener);
		
		checkUpdateLayout = (RelativeLayout)fragmentView.findViewById(R.id.settings_checkupdate_linearlayout);
		checkUpdateLayout.setOnClickListener(checkUpdateClickListener);
		return fragmentView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserUtil.isLogin(getActivity())) {
			loginTextView.setText(UserUtil.getPhone(getActivity()));
			logoutLayout.setVisibility(View.VISIBLE);
			loginArrowImageView.setVisibility(View.GONE);
		} else {
			loginTextView.setText("请登录");
			logoutLayout.setVisibility(View.GONE);
		}
	}

	private OnClickListener checkUpdateClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			UpdateManager manager = new UpdateManager(getActivity());
			manager.checkUpdate();
		}
	};
	private OnClickListener logoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//progressLayout.setVisibility(View.VISIBLE);
			HttpConnection.getClient().removeHeader("Cookie");
			UserUtil.logout(getActivity());
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					getActivity());
			if (myCookieStore == null)
				return;
			myCookieStore.clear();
			Toast toast = Toast.makeText(getActivity(), "注销成功",
					Toast.LENGTH_SHORT);
			toast.show();
			onResume();
		}
	};
	private OnClickListener loginClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (UserUtil.isLogin(getActivity())) {
				return;
			} else {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SignInActivity.class);
				startActivity(intent);
			}
		}
	};
	
//	private void signOutData(){
//		String url = GlobalData.getBaseUrl()+"/dealers/sign_in.json";
//		String phone = signinPhoneEditText.getText().toString();
//		String password = signinPasswordeEditText.getText().toString();
//		User user = new User();
//		user.setPhone(phone);
//		user.setPassword(password);
//		UserEntity userEntity = new UserEntity();
//		userEntity.setDealer(user);
//		Gson gson = new Gson();
//        StringEntity entity = null;
//        try {
//        	System.out.println(gson.toJson(userEntity).toString());
//			entity = new StringEntity(gson.toJson(userEntity).toString());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        HttpConnection.setCookie(getApplicationContext());
//		HttpConnection.post(SignInActivity.this, url, null, entity, "application/json;charset=utf-8", new JsonHttpResponseHandler(){
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					Throwable throwable, JSONObject errorResponse) {
//				// TODO Auto-generated method stub
//				super.onFailure(statusCode, headers, throwable, errorResponse);
//				progressLayout.setVisibility(View.GONE);
//				System.out.println("error");
//				System.out.println("statusCode:"+statusCode);
//				System.out.println("headers:"+headers);
////				for(int i = 0;i<headers.length;i++){
////					System.out.println(headers[i]);
////				}
//				System.out.println("response:"+errorResponse);
//				Toast toast = Toast.makeText(SignInActivity.this, "登录失败，重新登录", Toast.LENGTH_SHORT);
//				toast.show();
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					JSONObject response) {
//				// TODO Auto-generated method stub
//				super.onSuccess(statusCode, headers, response);
//				System.out.println("success");
//				System.out.println("statusCode:"+statusCode);
//				
//				for(int i=0;i<headers.length;i++){
//					System.out.println(headers[0]);
//				}
//				System.out.println("response:"+response);
//				progressLayout.setVisibility(View.GONE);
//				String id=null;
//				try {
//					 id = response.getString("id");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}					
//				UserUtil.login(SignInActivity.this);
//				UserUtil.setUserId(SignInActivity.this, id);
//				Toast toast = Toast.makeText(SignInActivity.this, "登录成功", Toast.LENGTH_SHORT);
//				toast.show();
//				getActivity().finish();
//				
//			}
//			
//		});
//	}

}

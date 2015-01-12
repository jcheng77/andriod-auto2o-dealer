package com.cettco.buycar.dealer.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.activity.MipcaActivityCapture;
import com.cettco.buycar.dealer.activity.MyOrderActivity;
import com.cettco.buycar.dealer.utils.UserUtil;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class WelcomeFragment extends Fragment {
	private View fragmentView;
	private AutoScrollViewPager autoScrollViewPager;
	private WelcomePagerAdapter adapter;
	private ArrayList<ImageView> arrayList;
	private LinearLayout ordersLayout;
	//private LinearLayout scanLayout;
	private LinearLayout appointmentLayout;
	private LinearLayout reportLayout;
	private LinearLayout complaintLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_welcome, container,
				false);
		ordersLayout = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_welcome_orders_layout);
		ordersLayout.setOnClickListener(ordersClickListener);
		//scanLayout = (LinearLayout) fragmentView
//				.findViewById(R.id.fragment_welcome_appointment_layout);
//		scanLayout.setOnClickListener(scanClickListener);
		appointmentLayout = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_welcome_appointment_layout);
		appointmentLayout.setOnClickListener(appointClickListener);
		reportLayout = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_welcome_report_layout);
		reportLayout.setOnClickListener(reportClickListener);
		complaintLayout = (LinearLayout) fragmentView
				.findViewById(R.id.fragment_welcome_complaint_layout);
		complaintLayout.setOnClickListener(complaintClickListener);
		
		//scan
		autoScrollViewPager = (AutoScrollViewPager) fragmentView
				.findViewById(R.id.welcome_view_pager);
		autoScrollViewPager.setInterval(4000);
		autoScrollViewPager.setCycle(true);
		arrayList = new ArrayList<ImageView>();
		adapter = new WelcomePagerAdapter(arrayList);
		for (int i = 0; i < 1; i++) {
			ImageView view = new ImageView(getActivity());
			view.setBackgroundResource(R.drawable.dealer_icon);
			arrayList.add(view);
		}
		autoScrollViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// autoScrollViewPager.startAutoScroll();
		return fragmentView;
	}

	protected class WelcomePagerAdapter extends PagerAdapter {

		private List<ImageView> list;

		public WelcomePagerAdapter(List<ImageView> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position), 0);
			return list.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list == null ? 0 : list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	private OnClickListener ordersClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(getActivity())) {
				Toast toast = Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(getActivity(), MyOrderActivity.class);
			startActivity(intent);
		}
	};
	private OnClickListener scanClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(getActivity())) {
				Toast toast = Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(getActivity(), MipcaActivityCapture.class);
			startActivity(intent);
		}
	};
	private OnClickListener appointClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(getActivity())) {
				Toast toast = Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Toast toast = Toast.makeText(getActivity(), "该功能还在完善中，敬请期待",
					Toast.LENGTH_SHORT);
			toast.show();
			
		}
	};
private OnClickListener complaintClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(getActivity())) {
				Toast toast = Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Toast toast = Toast.makeText(getActivity(), "该功能还在完善中，敬请期待",
					Toast.LENGTH_SHORT);
			toast.show();
			
		}
	};
private OnClickListener reportClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!UserUtil.isLogin(getActivity())) {
				Toast toast = Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Toast toast = Toast.makeText(getActivity(), "该功能还在完善中，敬请期待",
					Toast.LENGTH_SHORT);
			toast.show();
			
		}
	};

	private void checkLogin() {

	}

	private void alertIsDevelopment() {

	}
}

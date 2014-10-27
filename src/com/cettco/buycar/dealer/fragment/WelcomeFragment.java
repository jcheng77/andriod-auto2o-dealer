package com.cettco.buycar.dealer.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.cettco.buycar.R;
import com.cettco.buycar.dealer.activity.MyOrderActivity;
import com.cettco.buycar.dealer.activity.OrderHasDealerActivity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeFragment extends Fragment {
	private View fragmentView;
	private AutoScrollViewPager autoScrollViewPager;
	private WelcomePagerAdapter adapter;
	private ArrayList<ImageView> arrayList;
	private LinearLayout ordersLayout;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_welcome, container,
				false);
		ordersLayout = (LinearLayout)fragmentView.findViewById(R.id.fragment_welcome_orders_layout);
		ordersLayout.setOnClickListener(ordersClickListener);
		autoScrollViewPager = (AutoScrollViewPager)fragmentView.findViewById(R.id.welcome_view_pager);
		autoScrollViewPager.setInterval(2000);
		autoScrollViewPager.setCycle(true);
		arrayList = new ArrayList<ImageView>();
		adapter = new WelcomePagerAdapter(arrayList);
		for(int i=0;i<5;i++){
			ImageView view = new ImageView(getActivity());
			view.setBackgroundResource(R.drawable.welcome);
			arrayList.add(view);
		}
		autoScrollViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		autoScrollViewPager.startAutoScroll();
		return fragmentView;
	}
	protected class WelcomePagerAdapter extends PagerAdapter{

		private List<ImageView>list;
		public WelcomePagerAdapter(List<ImageView> list){
			this.list = list;
		}
		 @Override  
		    public void destroyItem(ViewGroup container, int position, Object object)   {     
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
			return list==null?0:list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1; 
		}
		
	}
	private OnClickListener ordersClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getActivity(),MyOrderActivity.class);
			startActivity(intent);
		}
	};
}

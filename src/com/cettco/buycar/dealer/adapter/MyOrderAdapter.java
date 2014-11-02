
package com.cettco.buycar.dealer.adapter;

import java.util.List;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.entity.OrderItemEntity;
import com.cettco.buycar.dealer.utils.MyApplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyOrderAdapter extends ArrayAdapter<OrderItemEntity> {

	private Context context;
	private List<OrderItemEntity> list;

	public MyOrderAdapter(Context context, int resource,
			List<OrderItemEntity> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	public void updateList(List<OrderItemEntity> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : this.list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_my_order, parent,
					false);
			holder.stateTextView = (TextView) convertView
					.findViewById(R.id.my_order_status_textview);
			holder.modeltextView = (TextView) convertView
					.findViewById(R.id.my_order_model_textview);
			holder.pricetextView = (TextView) convertView
					.findViewById(R.id.my_order_price_textview);
			holder.stateLayout = (LinearLayout) convertView
					.findViewById(R.id.my_order_status_layout);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.my_order_imageview);
			convertView.setTag(holder);
			;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		OrderItemEntity entity = list.get(position);
//		holder.modeltextView.setText(entity.getName());
//		holder.pricetextView.setText(entity.getPrice());
//		Data.IMAGE_CACHE.get(entity.getPic_url(), holder.imageView);
//		System.out.println("state:"+entity.getState());
//		if (entity.getState().equals("viewed")) {
//			holder.stateTextView.setText("以看车型");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF6600"));
//		} else if (entity.getState().equals("begain")) {
//			holder.stateTextView.setText("决定购买车型");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		} else if (entity.getState().equals("determined")) {
//			holder.stateTextView.setText("已提交订单,待支付");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		} else if (entity.getState().equals("qualified")) {
//			holder.stateTextView.setText("已支付");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		} else if (entity.getState().equals("timeout")) {
//			holder.stateTextView.setText("超时");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		} else if (entity.getState().equals("sumbitted")) {
//			holder.stateTextView.setText("已有4s店接单");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		} else if (entity.getState().equals("final_deal_closed")) {
//			holder.stateTextView.setText("最终成交");
//			holder.stateLayout.setBackgroundColor(Color.parseColor("#FF0033"));
//		}
		return convertView;
	}

	private static class ViewHolder {
		TextView stateTextView;
		TextView modeltextView;
		TextView pricetextView;
		LinearLayout stateLayout;
		ImageView imageView;
	}

}

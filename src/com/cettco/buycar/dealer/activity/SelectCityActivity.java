package com.cettco.buycar.dealer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cettco.buycar.dealer.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.LiveFolders;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectCityActivity extends Activity{

	private ArrayList<String> arrayList;
	private ListView listView;
	private TextView currentCitytTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectcity);
		//getActionBar().hide();
		listView = (ListView)findViewById(R.id.city_listview);
		arrayList = new ArrayList<String>();
		arrayList.add("上海");
		arrayList.add("北京");
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
		        android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listViewClickListener);
		
		//current city
		currentCitytTextView = (TextView)findViewById(R.id.currentCityTextview);
		SharedPreferences settings = getSharedPreferences("city_selection", 0);
		int selection = settings.getInt("city", 0);
		switch (selection) {
		case 0:
			currentCitytTextView.setText("当前城市:"+"上海");
			break;
		case 1:
			currentCitytTextView.setText("当前城市:"+"北京");
			break;
		default:
			break;
		}
	}
	protected OnItemClickListener listViewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			SharedPreferences settings = getSharedPreferences("city_selection", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("city", position);
			editor.commit();
			switch (position) {
			case 0:
				currentCitytTextView.setText("当前城市:"+"上海");
				break;
			case 1:
				currentCitytTextView.setText("当前城市:"+"北京");
				break;
			default:
				break;
			}
		}
	};
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	public void exitClick(View view)
	{
		finish();
	}
	
}

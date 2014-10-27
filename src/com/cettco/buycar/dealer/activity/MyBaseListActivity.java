package com.cettco.buycar.dealer.activity;

import java.util.ArrayList;

import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.adapter.MyBaseListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MyBaseListActivity extends Activity{

	private ListView listView;
	private MyBaseListAdapter adapter;
	private String name;
	private ArrayList<String> arrayList = new ArrayList<String>();
	private int tag;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_baselist);
		//getActionBar().hide();
		TextView titleTextView = (TextView)findViewById(R.id.title_text);
		intent = getIntent();
		name = intent.getStringExtra("name");
		tag = intent.getIntExtra("tag",0);
		titleTextView.setText(name);
		arrayList = intent.getStringArrayListExtra("list");
		listView = (ListView)findViewById(R.id.myBaseListview);
		adapter = new MyBaseListAdapter(this,android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listItemClickListener);
	}
	protected OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println("tag:"+tag);
			intent.putExtra("result", position);
			setResult(RESULT_OK, intent);
			MyBaseListActivity.this.finish();
		}
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		System.out.println("back pressed");
		setResult(RESULT_CANCELED, intent);
		//this.finish();
	}

	public void exitClick(View view){
		setResult(RESULT_CANCELED, intent);
		this.finish();
	}
	

}

package com.cettco.buycar.dealer.activity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.Header;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cettco.buycar.dealer.R;
import com.cettco.buycar.dealer.entity.OrderDetailEntity;
import com.cettco.buycar.dealer.utils.GlobalData;
import com.cettco.buycar.dealer.utils.HttpConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class OrderHasDealerActivity extends Activity {

//	private MapView mMapView = null;
//	private BaiduMap mBaiduMap;
	private ImageView qrImageView;
	private String id;
	private OrderDetailEntity  detailEntity ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_order_has_dealer);
		
		id= getIntent().getStringExtra("id");
//		mMapView = (MapView) findViewById(R.id.order_has_dealer_bmapView);
//		mBaiduMap = mMapView.getMap();
//		// 普通地图
//		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//		// 定义Maker坐标点
//		LatLng point = new LatLng(39.963175, 116.400244);
//		// 构建Marker图标
//		BitmapDescriptor bitmap = BitmapDescriptorFactory
//				.fromResource(R.drawable.icon_marka);
//		// 构建MarkerOption，用于在地图上添加Marker
//		OverlayOptions option = new MarkerOptions().position(point)
//				.icon(bitmap);
//		// 在地图上添加Marker，并显示
//		mBaiduMap.addOverlay(option);
		
		qrImageView = (ImageView)findViewById(R.id.order_has_dealer_qr_image);
		getData();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		//mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		//mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		//mMapView.onPause();
	}

	protected void getData() {
		// String url = GlobalData.getBaseUrl() + "/cars/list.json";
		// httpCache.clear();
		String url=GlobalData.getBaseUrl()+"/tenders/"+id+".json";
		HttpConnection.setCookie(getApplicationContext());
		HttpConnection.get(url,new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				System.out.println("fail");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("seccuss");
				try {
					String result= new String(arg2,"UTF-8");
					System.out.println("result:"+result);
					Gson gson = new Gson();
					detailEntity = gson.fromJson(result, OrderDetailEntity.class);
//					Type listType = new TypeToken<ArrayList<DealerEntity>>() {
//					}.getType();
//					dealerList = new Gson().fromJson(result, listType);
//					System.out.println("size:"+dealerList.size());
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					//System.out.println("result:"+result);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("update");
				//dealerListAdapter.updateList(dealerList);
				updateUI();
				break;
			}
		};
	};
	private void updateUI(){
		try {
			qrImageView.setImageBitmap(Create2DCode(detailEntity.getVerfiy_code()));
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Bitmap Create2DCode(String str) throws WriterException {  
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败  
        BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, 300, 300);  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        //二维矩阵转为一维像素数组,也就是一直横着排了  
        int[] pixels = new int[width * height];  
        for (int y = 0; y < height; y++) {  
            for (int x = 0; x < width; x++) {  
                if(matrix.get(x, y)){  
                    pixels[y * width + x] = 0xff000000;  
                }  
                  
            }  
        }  
          
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);  
        //通过像素数组生成bitmap,具体参考api  
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        return bitmap;  
    }
	protected OnClickListener localMapBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("geo:39.922840,116.3543240,北京市西城区阜外大街2号万通大厦");
			intent.setData(uri);
			// intent.setPackage("com.baidu.BaiduMap");
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				uri = Uri.parse("http://api.map.baidu.com/geocoder?address="
						+ "上海虹桥机场" + "&output=html");
				Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
				intent2.setData(uri);
				startActivity(intent2);
			}
		}
	};

	public void exitClick(View view) {
		this.finish();
	}
}

package com.cettco.buycar.dealer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SquareView extends LinearLayout{

	public SquareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		int size = 0;
	    int width = getMeasuredWidth();
	    int height = getMeasuredHeight();
	    int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
	    int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();
	    System.out.println("width:"+widthWithoutPadding);
	    System.out.println("height:"+heigthWithoutPadding);
	    System.out.println("count:"+getChildCount());
	    //System.out.println("mode:"+getmo);
	    int n = getChildCount();
	    for(int i=0;i<n;i++){
	    	View v = getChildAt(i);
	    	int w = v.getMeasuredWidth();
	    	int h = v.getMeasuredWidth();
	    	System.out.println("child width:"+w);
	 	    System.out.println("child height:"+h);
	    }
	 
	    // set the dimensions
	    if (widthWithoutPadding>heigthWithoutPadding) {
	        size = heigthWithoutPadding;
	    } else {
	        size = widthWithoutPadding;
	    }
	 
	    setMeasuredDimension(size, size);
	}

}

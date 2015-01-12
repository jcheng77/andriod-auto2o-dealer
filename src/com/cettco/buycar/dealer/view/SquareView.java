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
	 
	    // set the dimensions
	    if (widthWithoutPadding>heigthWithoutPadding) {
	        size = heigthWithoutPadding;
	    } else {
	        size = widthWithoutPadding;
	    }
	 
	    setMeasuredDimension(size, size-80);
	}

}

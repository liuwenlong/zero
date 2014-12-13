package com.mapgoo.zero.ui.widget;

import com.mapgoo.zero.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NumberCtrlView  extends RelativeLayout {
	ImageView mLeft;
	ImageView mRight;
	TextView mNumber;
	int mCount;
	
	public NumberCtrlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = View.inflate(context, R.layout.number_control_layout, null);
		addView(view);
		mLeft = (ImageView)findViewById(R.id.number_control_left);
		mRight = (ImageView)findViewById(R.id.number_control_right);
		mNumber = (TextView)findViewById(R.id.number_control_middle);
		setNumBer(1);
		
		mLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reduceNumber();
			}
		});
		mRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addNumber();
			}
		});
		
	}
	
	private void setNumBer(int num){
		mCount = num;
		mNumber.setText(num+"");
	}
	
	public int getNumber(){
		return mCount;
	}
	
	private void addNumber(){
		int i = mCount;
		i++;
		setNumBer(i);
	}
	private void reduceNumber(){
		int i = mCount;
		i--;
		if(i>=0)
			setNumBer(i);
		else
			;
	}
	
}

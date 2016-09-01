package com.example.sideslipapp.sideslipview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class SideslipLayout extends FrameLayout{

	private SlidingMenu slidingMenu;
	
	private int mScreenWidth;

	private boolean once;
	
	private Context context;
	
	public SideslipLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		//super(context, attrs);
		//this.context = context;
	}

	public SideslipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public SideslipLayout(Context context) {
		//super(context);
		this(context, null, 0);
	}

	public void setSlidingMenu(SlidingMenu slidingMenu) {
		this.slidingMenu = slidingMenu;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!once){
			if(slidingMenu == null){
				slidingMenu = (SlidingMenu) getChildAt(1);
			}
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			mScreenWidth = outMetrics.widthPixels;
			Log.i("P1", mScreenWidth+"");
			once = true;
		}
	}
	
	private float sRawX = 0, eRawX = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action){
			case MotionEvent.ACTION_DOWN:
				sRawX = ev.getRawX();
				break;
			/*case MotionEvent.ACTION_MOVE:
				eRawX = ev.getRawX();
				Log.i("eRawX", "ACTION_MOVE:"+eRawX);
				break;*/
			case MotionEvent.ACTION_UP:
				eRawX = ev.getRawX();
				if (eRawX - sRawX > 0){
					/*viewTranslate(context, 0 , mScreenWidth - mMenuRightPadding);
					isOpen = false;*/
					slidingMenu.openMenu();
				} else if(eRawX - sRawX < 0 || sRawX > (mScreenWidth - slidingMenu.mMenuRightPadding)){
					/*viewTranslate(context, mScreenWidth - mMenuRightPadding, 0);
					isOpen = true;*/
					slidingMenu.closeMenu();
				}
				break;
		}
		//必须return true ACTION_DOWN和ACTION_UP才响应
		return true;
	}
	
}

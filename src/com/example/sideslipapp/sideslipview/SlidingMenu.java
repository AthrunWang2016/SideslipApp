package com.example.sideslipapp.sideslipview;

import com.example.sideslipapp.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

//因为继承自LinearLayout，所以xml中要设置orientation属性
public class SlidingMenu extends LinearLayout {

	private ViewGroup mContent;
	private int mScreenWidth;

	public int mMenuRightPadding = 60;

	private float sRawX = 0, eRawX = 0;
	
	private boolean once;

	private boolean isOpen;

	private Context context;
	
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		//super(context, attrs);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.context = context;
		/**
		 * 应该重写获取属性方法
		 * 1）设置背景色默认为白色
		 * 2）设置Orientation默认为VERTICAL
		 */
		/*setBackgroundColor(color.white);
		setOrientation(LinearLayout.VERTICAL);*/
		
		// 获取我们定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++){
			int attr = a.getIndex(i);
			switch (attr){
				case R.styleable.SlidingMenu_rightPadding:
					mMenuRightPadding = a.getDimensionPixelSize(attr,
							(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, context.getResources().getDisplayMetrics()));
					break;
			}
		}
		a.recycle();

		/*WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

		Log.i("1", mScreenWidth+"");*/
		
	}

	public SlidingMenu(Context context) {
		this(context, null);
		//super(context);
	}

	/**
	 * 获取屏幕宽度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!once){
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			mScreenWidth = outMetrics.widthPixels;
			Log.i("1", mScreenWidth+"");
			once = true;
		}
	}

	/*@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action){
			case MotionEvent.ACTION_DOWN:
				sRawX = ev.getRawX();
				break;
			case MotionEvent.ACTION_MOVE:
				eRawX = ev.getRawX();
				Log.i("eRawX", "ACTION_MOVE:"+eRawX);
				break;
			case MotionEvent.ACTION_UP:
				eRawX = ev.getRawX();
				if (eRawX - sRawX > 0){
					viewTranslate(context, 0 , mScreenWidth - mMenuRightPadding);
					isOpen = false;
					openMenu();
				} else if(eRawX - sRawX < 0 ||sRawX > mScreenWidth - mMenuRightPadding){
					viewTranslate(context, mScreenWidth - mMenuRightPadding, 0);
					isOpen = true;
					closeMenu();
				}
				break;
		}
		//必须return true ACTION_DOWN和ACTION_UP才响应
		return true;
	}*/

	/**
	 * 打开菜单
	 * 可以添加接口，在方法中调用
	 */
	public void openMenu(){
		if (isOpen)
			return;
		viewTranslate(context, 0 , mScreenWidth - mMenuRightPadding);
		isOpen = true;
	}

	/**
	 * 切换菜单
	 * 可以添加接口，在方法中调用
	 */
	public void closeMenu(){
		if (!isOpen)
			return;
		viewTranslate(context, mScreenWidth - mMenuRightPadding, 0);
		isOpen = false;
	}

	/**
	 * 获取菜单状态
	 */
	public boolean getStatus(){
		return isOpen;
	}
	
	private void viewTranslate(Context context,int fromXDelta,int toXDelta) {
		Animation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
		//设置动画持续时间为0.3秒
		translateAnimation.setDuration(300);
		//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
		translateAnimation.setFillAfter(true);
		startAnimation(translateAnimation);
	}
	
}

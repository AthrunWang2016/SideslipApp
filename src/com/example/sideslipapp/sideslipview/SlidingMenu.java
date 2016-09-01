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

//��Ϊ�̳���LinearLayout������xml��Ҫ����orientation����
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
		 * Ӧ����д��ȡ���Է���
		 * 1�����ñ���ɫĬ��Ϊ��ɫ
		 * 2������OrientationĬ��ΪVERTICAL
		 */
		/*setBackgroundColor(color.white);
		setOrientation(LinearLayout.VERTICAL);*/
		
		// ��ȡ���Ƕ��������
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
	 * ��ȡ��Ļ���
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
		//����return true ACTION_DOWN��ACTION_UP����Ӧ
		return true;
	}*/

	/**
	 * �򿪲˵�
	 * ������ӽӿڣ��ڷ����е���
	 */
	public void openMenu(){
		if (isOpen)
			return;
		viewTranslate(context, 0 , mScreenWidth - mMenuRightPadding);
		isOpen = true;
	}

	/**
	 * �л��˵�
	 * ������ӽӿڣ��ڷ����е���
	 */
	public void closeMenu(){
		if (!isOpen)
			return;
		viewTranslate(context, mScreenWidth - mMenuRightPadding, 0);
		isOpen = false;
	}

	/**
	 * ��ȡ�˵�״̬
	 */
	public boolean getStatus(){
		return isOpen;
	}
	
	private void viewTranslate(Context context,int fromXDelta,int toXDelta) {
		Animation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
		//���ö�������ʱ��Ϊ0.3��
		translateAnimation.setDuration(300);
		//���ö��������󱣳ֵ�ǰ��λ�ã��������ص�������ʼǰ��λ�ã�
		translateAnimation.setFillAfter(true);
		startAnimation(translateAnimation);
	}
	
}

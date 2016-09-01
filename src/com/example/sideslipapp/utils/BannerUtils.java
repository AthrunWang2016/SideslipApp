package com.example.sideslipapp.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.sideslipapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
/**
 * @author admin
 *
 */
public class BannerUtils{

	private View rr01;
	private ViewPager bPager;
	private int bImages[]={
			 R.drawable.banner01,R.drawable.banner02,
			 R.drawable.banner03
	};
	
	private int prePos;
	private boolean isScroller=true;
	private LinearLayout indicatorLayout;
	
	private Context context;
	
	public BannerUtils(Context context) {
		this.context = context;
		setViews();
		setBannerPager();
		setBannerScroller();
		setBannerIndicator();
		setBannerImageViewData();
	}
	
	private void setViews() {
		//LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		rr01 = View.inflate(context,R.layout.banner_vp_indicator, null);
		bPager = (ViewPager) rr01.findViewById(R.id.bannerVp01);
		indicatorLayout = (LinearLayout) rr01.findViewById(R.id.bannerIndicatorId);
	}
	
	public View getRelativeLayout(){
		return rr01;
	}
	
	private void setBannerPager(){
		bPager.setAdapter(new BannerAdapter());
		bPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int postion) {
				// TODO Auto-generated method stub
				indicatorLayout.getChildAt(prePos%bImages.length).setEnabled(true);
				indicatorLayout.getChildAt(postion%bImages.length).setEnabled(false);
				prePos=postion;
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	int currentItem = 0;
	private void setBannerScroller(){
		final Handler h=new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				bPager.setCurrentItem(currentItem);	
				currentItem++;
				if (currentItem == bImages.length) { 
					//bPager.setCurrentItem(0, false);
					currentItem = 0;
                }
				if(isScroller){
					h.postDelayed(this, 2000);
				}
			}
		}, 3000);
	}
	
	private void setBannerIndicator(){
		for(int i=0;i<bImages.length;i++){
			View v=new View(context);
			v.setEnabled(true);
			v.setBackgroundResource(R.drawable.banner_indicator_selector);
			LayoutParams params = new LayoutParams(15, 15);
			params.rightMargin=10;
			v.setLayoutParams(params);
			indicatorLayout.addView(v);
		}
		indicatorLayout.getChildAt(0).setEnabled(false);	
	}
	
	private List<ImageView> imageViews = new ArrayList<ImageView>();
	
	private void setBannerImageViewData(){
		for(int i=0;i<bImages.length;i++){
			ImageView imageView=new ImageView(context);
			imageView.setImageResource(bImages[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageViews.add(imageView);
		}
	}
	
	class BannerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			//return Integer.MAX_VALUE;
			return bImages.length;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			/*ImageView imageView = new ImageView(context);
			//imageView.setImageResource(bImages[position%bImages.length]);
			imageView.setImageBitmap(readBitMap(context,bImages[position%bImages.length]));
			imageView.setScaleType(ScaleType.FIT_XY);*/
			ImageView imageView = imageViews.get(position);
			container.addView(imageView);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				 //Toast.makeText(getActivity(),"pos="+(position%bImages.length), 1).show();
				}
			});
			return imageView;
		}
	}
	class ViewHolder{
		ImageView imageView;
	}
	
}

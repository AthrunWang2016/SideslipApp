package com.example.sideslipapp;

import com.example.sideslipapp.sideslipview.SlidingMenu;
import com.example.sideslipapp.utils.BannerUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SlidingMenu mLeftMenu ; 
	private RelativeLayout tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		tv1 = (RelativeLayout) findViewById(R.id.tv1);
		tv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "µÚÒ»¸öItem", 0).show();
			}
		});
		mLeftMenu.addView(new BannerUtils(MainActivity.this).getRelativeLayout());
	}

	public void toggleMenu(View view){
			if (mLeftMenu.getStatus()){
				mLeftMenu.closeMenu();
			} else{
				mLeftMenu.openMenu();
			}
	}
	
}

package com.example.helloandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainHelloAndroid extends ActionBarActivity {
	String tag = "[MainActivityLife]";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");
		setContentView(R.layout.activity_main_hello_android);
		
		final ImageView iv = (ImageView)findViewById(R.id.imageView1);
		final ImageButton ib = (ImageButton)findViewById(R.id.imageButton1);
		
		OnTouchListener otl = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				Toast.makeText(getApplicationContext(), "触摸" + ((ImageView)view).getId(), Toast.LENGTH_LONG).show();
				
				return false;
			}
		};
		
		OnClickListener ocl = new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "点击" + ((ImageView)view).getId(), Toast.LENGTH_LONG).show();
			}
		};
		
		iv.setOnTouchListener(otl);
		iv.setOnClickListener(ocl);
		ib.setOnTouchListener(otl);
		ib.setOnClickListener(ocl);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_hello_android, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(tag, "onStart");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.i(tag, "onPause");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.i(tag, "onResume");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.i(tag, "onStop");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i(tag, "onDestroy");
	}
}

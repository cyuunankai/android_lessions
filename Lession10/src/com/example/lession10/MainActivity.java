package com.example.lession10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn1 = (Button) findViewById(R.id.button1);
		Button btn2 = (Button) findViewById(R.id.button2);
		Button btn3 = (Button) findViewById(R.id.button3);
		
		OnClickListener ocl = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button btn = (Button)v;
				switch (btn.getId()) {
				case R.id.button1:
					startActivity(new Intent(v.getContext(), SpinnerActivity.class));
					break;
				case R.id.button2:
					startActivity(new Intent(v.getContext(), AutoCompleteTextViewActivity.class));
					break;
				case R.id.button3:
					startActivity(new Intent(v.getContext(), DatePickerActivity.class));
					break;

				default:
					break;
				}
			}
		};
		
		btn1.setOnClickListener(ocl);
		btn2.setOnClickListener(ocl);
		btn3.setOnClickListener(ocl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}

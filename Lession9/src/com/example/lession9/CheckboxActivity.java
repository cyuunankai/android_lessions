package com.example.lession9;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class CheckboxActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox);
		
		CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
		CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
		
		OnClickListener ocl = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox)v;
				if(!cb.isChecked()){
					Toast.makeText(getApplicationContext(), cb.getText() + "被取消", Toast.LENGTH_SHORT).show();
				}
				
			}
		};
		
		OnCheckedChangeListener occl = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton cBtn, boolean isChecked) {
				if(isChecked){
					Toast.makeText(getApplicationContext(), cBtn.getText() + "被选择", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		cb1.setOnClickListener(ocl);
		cb2.setOnClickListener(ocl);
		cb1.setOnCheckedChangeListener(occl);
		cb2.setOnCheckedChangeListener(occl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkbox, menu);
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

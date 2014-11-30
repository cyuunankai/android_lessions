package com.example.timepickertest;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.timepickertest.utils.StringUtils;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setDateBtnText();
		setStartTimeBtnText();
	}
	
	private void setDateBtnText(){
		Button dateBtn = (Button)findViewById(R.id.date);
		
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
		dateBtn.setText(year + "/" +StringUtils.leftPadTwo(month + 1) + "/" + StringUtils.leftPadTwo(day));
	}
	
	private void setStartTimeBtnText() {
		Button startTime = (Button)findViewById(R.id.startTime);
		
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		startTime.setText(StringUtils.leftPadTwo(hour) + ":" + StringUtils.leftPadTwo(minute));
	}
	
	public void showDatePickerDialog(View v) {
		Button b = (Button)v;
		
	    DialogFragment newFragment = new DatePickerFragment(b);
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		Button b = (Button)v;
		
	    DialogFragment newFragment = new TimePickerFragment(b);
	    newFragment.show(getSupportFragmentManager(), "startTimePicker");
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

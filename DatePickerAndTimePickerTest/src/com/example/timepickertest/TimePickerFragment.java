package com.example.timepickertest;

import com.example.timepickertest.utils.StringUtils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	Button mBtn = null;
	int mHour;
	int mMinute;
	
	public TimePickerFragment(Button btn){
		String buttonText = btn.getText().toString();
		String [] arr = buttonText.split(":");
		int hour = Integer.valueOf(arr[0]);
		int minute = Integer.valueOf(arr[1]);
		
		mBtn = btn;
		mHour = hour;
		mMinute = minute;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		int hour = mHour;
		int minute = mMinute;

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mBtn.setText(StringUtils.leftPadTwo(hourOfDay) + ":" + StringUtils.leftPadTwo(minute));
	}
}


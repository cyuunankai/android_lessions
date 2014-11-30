package com.example.timepickertest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.timepickertest.utils.StringUtils;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	Button mBtn = null;
	int mYear;
	int mMonth;
	int mDay;
	
	public DatePickerFragment(Button btn){
		
		String buttonText = btn.getText().toString();

		String [] arr = buttonText.split("/");
		int year = Integer.valueOf(arr[0]);
        int month = Integer.valueOf(arr[1]);
        int day = Integer.valueOf(arr[2]);
        
        mBtn = btn;
		mYear = year;
		mMonth = month;
		mDay = day;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, mYear, mMonth - 1, mDay);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		mBtn.setText(year + "/" +StringUtils.leftPadTwo(month + 1) + "/" + StringUtils.leftPadTwo(day));
	}
}


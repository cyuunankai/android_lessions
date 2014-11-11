package com.example.lession10;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerActivity extends ActionBarActivity {

	private TextView dateDisplay;
    private Button pickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
	static final int DATE_DIALOG_ID = 0;
	
	OnDateSetListener dsl = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Log.i("DatePickerActivity", "onDateSet");
			mYear =year;
            mMonth= month;
            mDay= day;
            dateDisplay.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
		}
	};
	
	@Override
    protected Dialog onCreateDialog(int id){
        switch(id){
        case DATE_DIALOG_ID:
                //把Activity上下文、日期设置监听器、以及日期的年月日都作为参数传给那个新成立的对话框组件
                return new DatePickerDialog(this, dsl, mYear, mMonth, mDay);
        }
        return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker);
		
		//得到当前日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //获取xml中的组件
        dateDisplay= (TextView)findViewById(R.id.textView1);
        pickDate = (Button)findViewById(R.id.button1);

        //给日期显示组件一个初始值
        dateDisplay.setText(mYear+"-"+(mMonth+1)+"-"+mDay);

        //给按钮绑定点击监听器
        pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_picker, menu);
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

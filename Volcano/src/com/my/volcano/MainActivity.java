package com.my.volcano;

import java.io.File;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.my.volcano.schedule.ChangeLockScreenPicBroadcastRecevier;
import com.my.volcano.schedule.NotificateBroadcastRecevier;
import com.my.volcano.util.DateUtil;
import com.my.volcano.util.LogUtil;

public class MainActivity extends ActionBarActivity {

	Context mContext;
	ChangeLockScreenPicBroadcastRecevier clspbr;
	NotificateBroadcastRecevier nbr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		File logFile = new File(Environment.getExternalStorageDirectory() + "/volcanoLog.txt");
		logFile.delete();
		
		mContext = getApplicationContext();
		clspbr = new ChangeLockScreenPicBroadcastRecevier();
		nbr = new NotificateBroadcastRecevier();
		LogUtil.appendLog(DateUtil.getSysTimeStr() + " onCreate start");
			
	}
	
	public void pendingBtnClickListener(View v){
		int hour = 20;
		int min = 0;
		clspbr.SetAlarm(mContext, hour, min);
		
		//min = min + 1;
		nbr.SetAlarm(mContext, hour, min);
	}
	
	public void lockScreenPicBtnClickListener(View v){
		Toast.makeText(mContext, "锁屏挂起" + checkIfChangeLockScreenPicPendingIntentIsRegistered(), Toast.LENGTH_SHORT).show();
	}
	
	public void notificateBtnClickListener(View v){
		Toast.makeText(mContext, "通知挂起" + checkIfNotificatePendingIntentIsRegistered(), Toast.LENGTH_SHORT).show();
	}
	
	public void cancelPendingBtnClickListener(View v){
		clspbr.CancelAlarm(mContext);
		nbr.CancelAlarm(mContext);
	}
	
	public void resetPendingBtnClickListener(View v){
		LogUtil.appendLog(DateUtil.getSysTimeStr() + " reset start");
		int hour = 20;
		int min = 5;
		clspbr.SetAlarm(mContext, hour, min);
		
//		min = min + 1;
		nbr.SetAlarm(mContext, hour, min);
		
		LogUtil.appendLog(DateUtil.getSysTimeStr() + " reset end");
	}
	
	private boolean checkIfChangeLockScreenPicPendingIntentIsRegistered() {
	    Intent intent = new Intent(mContext, ChangeLockScreenPicBroadcastRecevier.class);
	    // Build the exact same pending intent you want to check.
	    // Everything has to match except extras.
	    return (PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
	}
	
	private boolean checkIfNotificatePendingIntentIsRegistered() {
	    Intent intent = new Intent(mContext, NotificateBroadcastRecevier.class);
	    // Build the exact same pending intent you want to check.
	    // Everything has to match except extras.
	    return (PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
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

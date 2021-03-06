package com.my.volcano.schedule;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.my.volcano.service.NotificationService;
import com.my.volcano.util.DateUtil;
import com.my.volcano.util.LogUtil;

public class NotificateBroadcastRecevier extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 
		
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();
         
         Intent i = new Intent(context, NotificationService.class);
         context.startService(i);
         
         LogUtil.appendLog(DateUtil.getSysTimeStr() + " execute 'notificate' pending event ");
         
         //Release the lock
         wl.release();
                  
	}
	
	public void SetAlarm(Context context, int hour, int min)
    {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		
		
		
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificateBroadcastRecevier.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*1, pi);
        
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NotificateBroadcastRecevier.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        
        LogUtil.appendLog("cancel 'notificate' pending event");
    }

}

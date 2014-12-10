package com.example.getgeonameandweather.schdule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.getgeonameandweather.LocalWeather;
import com.example.getgeonameandweather.LocationSearch;
import com.example.getgeonameandweather.R;
import com.example.getgeonameandweather.ResultActivity;
import com.example.getgeonameandweather.bean.LocationData;
import com.example.getgeonameandweather.bean.Weather;
import com.example.getgeonameandweather.bean.WeatherAndLocation;
import com.example.getgeonameandweather.db.WildFishingDatabase;


public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	LocationManager locationManager;
	Location location;
	LocationListener locationListener;
	boolean network_enabled=false;
	Context mContext;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();
         mContext = context;
         registLocationListener(mContext);
         
         // for debug
//	     String qLocation = "41.73,123.47";
//	   	 new RetrieveWeatherTask().execute(qLocation);
         //Release the lock
         wl.release();
         
//         CancelAlarm(mContext);
         
	}
	
	public void SetAlarm(Context context)
    {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 20); // For 20 AM
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        // per day 1 AM
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
		
		// for debug
//		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//        //After after 30 seconds
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 15 , pi); 
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    
    public void registLocationListener(Context context) {
		
		boolean network_enabled=false;

		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		try {
			network_enabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			Toast.makeText(context, "network is not enable ",
					Toast.LENGTH_SHORT).show();
		}

		// don't start listeners if no provider is enabled
		if (!network_enabled)
			return;
//		Toast.makeText(context, "is ok ", Toast.LENGTH_SHORT).show();
		location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		// 位置监听器
        locationListener = new LocationListener() {

                 // 当位置改变时触发
                 @Override
                 public void onLocationChanged(Location location) {
                     updateLocation(location);
                 }

                 // Provider失效时触发
                 @Override
                 public void onProviderDisabled(String arg0) {

                 }

                 // Provider可用时触发
                 @Override
                 public void onProviderEnabled(String arg0) {
                 }

                 // Provider状态改变时触发
                 @Override
                 public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                 }
         };

         // 500毫秒更新一次，忽略位置变化
         locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locationListener);
	}
    
    private void updateLocation(Location paramLocation) {
        if (paramLocation != null) {
        	// 得到地理位置
        	String qLocation = Double.toString(paramLocation.getLatitude()) + "," + Double.toString(paramLocation.getLongitude());
        	// 获取天气和城市名
        	new RetrieveWeatherTask().execute(qLocation);
        	// 删除location监听
        	locationManager.removeUpdates(locationListener);
        } else {
            Toast.makeText(
                    mContext,
                    "cannot get location  ", Toast.LENGTH_SHORT).show();
        }
	}
    
    private String getSystemDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
	// 异步任务，获取天气和地区信息
	class RetrieveWeatherTask extends AsyncTask<String, Void, WeatherAndLocation> {

		public static final boolean LOGD = true;

	    protected WeatherAndLocation doInBackground(String... locations) {
	    	WeatherAndLocation wal = new WeatherAndLocation();
	    	
	    	//get weather
	    	LocalWeather lw = new LocalWeather(true);
			String query = (lw.new Params("2c6cd1603148c8db1d40a83880a94")).setQ(locations[0]).setDate(getSystemDate()).getQueryString(LocalWeather.Params.class);
			Weather weatherData = lw.callAPI(query);
			
			//get location
			LocationSearch ls = new LocationSearch(true);
			query = (ls.new Params("2c6cd1603148c8db1d40a83880a94")).setQuery(locations[0]).getQueryString(LocationSearch.Params.class);
			LocationData locationData = ls.callAPI(query);
			
			wal.setWeatherData(weatherData);
			wal.setLocationData(locationData);
			
			return wal;
	    }
	    
	    protected void onPostExecute(WeatherAndLocation wal) {
//	    	weatherTextView.setText(wal.getWeatherData().weather.maxtempC);
//	    	geoTextView.setText(wal.getLocationData().areaName);
	    	WildFishingDatabase wfd = new WildFishingDatabase(mContext);
	    	wfd.addWeatherData(wal);
	    	
	    	notificate(wfd.getWeathers());
	    	Log.i("", "");
	    }

	 
	}
	
	public void notificate(String msg){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(mContext)
		        .setSmallIcon(R.drawable.abc_ab_bottom_solid_dark_holo)
		        .setContentTitle("收集天气")
		        .setContentText(msg);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(mContext, ResultActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(ResultActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());

	}

}

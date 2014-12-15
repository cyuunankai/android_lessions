package com.example.getgeonameandweather.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.getgeonameandweather.LocalWeather;
import com.example.getgeonameandweather.LocationSearch;
import com.example.getgeonameandweather.R;
import com.example.getgeonameandweather.ResultActivity;
import com.example.getgeonameandweather.bean.LocationData;
import com.example.getgeonameandweather.bean.Weather;
import com.example.getgeonameandweather.bean.WeatherAndLocation;
import com.example.getgeonameandweather.db.WildFishingDatabase;

public class SaveWeatherAndLocationService extends IntentService {
	
	Handler mMainThreadHandler = null;
	
	LocationManager locationManager;
	LocationListener locationListener;
	boolean network_enabled=false;
	
	/** 
	   * A constructor is required, and must call the super IntentService(String)
	   * constructor with a name for the worker thread.
	   */
	  public SaveWeatherAndLocationService() {
	      super("SaveWeatherAndLocationService");
	      
	      mMainThreadHandler = new Handler();
	  }

	  /**
	   * The IntentService calls this method from the default worker thread with
	   * the intent that started the service. When this method returns, IntentService
	   * stops the service, as appropriate.
	   */
	  @Override
	  protected void onHandleIntent(Intent intent) {
		  mMainThreadHandler.post(new Runnable() {
	            @Override
	            public void run() {
	            	registLocationListener();
	            }
	        });
	  }
	  
	  public void registLocationListener() {
			
			boolean network_enabled=false;

			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			try {
				network_enabled = locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			} catch (Exception ex) {
			}

			// don't start listeners if no provider is enabled
			if (!network_enabled)
				return;
			locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

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
	    
	    private void updateLocation(Location location) {
	        if (location != null) {
	        	// 得到地理位置
	        	String qLocation = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
	        	// 获取天气和城市名
	        	new RetrieveWeatherTask().execute(qLocation);
	        	// 删除location监听
	        	locationManager.removeUpdates(locationListener);
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
		    	WildFishingDatabase wfd = new WildFishingDatabase(getApplicationContext());
		    	wfd.addWeatherData(wal);
		    	
		    	notificate(wfd.getWeathers());
		    	Log.i("", "");
		    }

		 
		}
		
		public void notificate(String msg){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(this)
			        .setSmallIcon(R.drawable.abc_ab_bottom_solid_dark_holo)
			        .setContentTitle("收集天气")
			        .setContentText(msg);
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(this, ResultActivity.class);

			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
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
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(1, mBuilder.build());

		}
	  

}

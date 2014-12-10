package com.example.getgeonameandweather;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getgeonameandweather.bean.LocationData;
import com.example.getgeonameandweather.bean.Weather;
import com.example.getgeonameandweather.bean.WeatherAndLocation;
import com.example.getgeonameandweather.db.WildFishingDatabase;
import com.example.getgeonameandweather.schdule.AlarmManagerBroadcastReceiver;

public class MainActivity extends ActionBarActivity {

	LocationManager locationManager;
	Location location;
	LocationListener locationListener;
	boolean network_enabled=false;
	
	TextView weatherTextView;
	TextView geoTextView;
	
	private AlarmManagerBroadcastReceiver alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		weatherTextView = (TextView) findViewById(R.id.textView1);
		geoTextView = (TextView) findViewById(R.id.textView2);
 		
		alarm = new AlarmManagerBroadcastReceiver();
//		WildFishingDatabase wfd = new WildFishingDatabase(getApplicationContext());
//		wfd.addWeather(new WeatherAndLocation());
		
        // for debug (debug cannot get location by GPS,network...  etc)
//        String qLocation = "41.73,123.47";
//    	new RetrieveWeatherTask().execute(qLocation);
    	
//    	registLocationListener();

		
	}
	
	public void startSchdule(View view){
		alarm.SetAlarm(getApplicationContext());
	}
	
	public void showWeather(View view){
		WildFishingDatabase wfd = new WildFishingDatabase(this);
		Toast.makeText(getBaseContext(), wfd.getWeathers(),
				Toast.LENGTH_SHORT).show();
		
	}
	
	public void noficate(View view){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.abc_ab_bottom_solid_dark_holo)
		        .setContentTitle("My notification")
		        .setContentText("Hello World!");
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
	
	public void registLocationListener() {
		
		boolean network_enabled=false;

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			network_enabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			Toast.makeText(getBaseContext(), "network is not enable ",
					Toast.LENGTH_SHORT).show();
		}

		// don't start listeners if no provider is enabled
		if (!network_enabled)
			return;
		Toast.makeText(getBaseContext(), "is ok ", Toast.LENGTH_SHORT).show();
		location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		// 位置监听器
        locationListener = new LocationListener() {

                 // 当位置改变时触发
                 @Override
                 public void onLocationChanged(Location location) {
                     Toast.makeText(
                             getBaseContext(),
                             "onLocationChanged", Toast.LENGTH_SHORT).show();
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
                    getBaseContext(),
                    "cannot get location  ", Toast.LENGTH_SHORT).show();
        }
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
			String query = (lw.new Params(lw.key)).setQ(locations[0]).setDate(getSystemDate()).getQueryString(LocalWeather.Params.class);
			Weather weatherData = lw.callAPI(query);
			
			//get location
			LocationSearch ls = new LocationSearch(true);
			query = (ls.new Params(ls.key)).setQuery(locations[0]).getQueryString(LocationSearch.Params.class);
			LocationData locationData = ls.callAPI(query);
			
			wal.setWeatherData(weatherData);
			wal.setLocationData(locationData);
			
			return wal;
	    }
	    
	    protected void onPostExecute(WeatherAndLocation wal) {
//	    	weatherTextView.setText(wal.getWeatherData().weather.maxtempC);
//	    	geoTextView.setText(wal.getLocationData().areaName);
	    	WildFishingDatabase wfd = new WildFishingDatabase(getApplicationContext());
	    	wfd.addWeatherData(wal);
	    	Log.i("", "");
	    }

	 
	}
	
	
}

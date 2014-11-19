package com.example.lesson26_gpslocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

        TextView editLocation;
        Location location;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // ����UI���
                Button b1 = (Button) findViewById(R.id.button1);
                editLocation = (TextView) findViewById(R.id.textView1);
                
                boolean gps_enabled=false;
                boolean network_enabled=false;

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
			Toast.makeText(
                    getBaseContext(),
                    "gps is not enable ", Toast.LENGTH_SHORT).show();
		}
		try {
			network_enabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			Toast.makeText(
                    getBaseContext(),
                    "network is not enable ", Toast.LENGTH_SHORT).show();
		}

                //don't start listeners if no provider is enabled
                if(!gps_enabled && !network_enabled)
                    return;
                Toast.makeText(
                        getBaseContext(),
                        "is ok ", Toast.LENGTH_SHORT).show();
//                
//                LocationListener locationListener = new MyLocationListener();
//                locationManager.requestLocationUpdates(
//                		LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            updateLocation(location);
                    }
            });
                
             // λ�ü�����
                LocationListener locationListener = new LocationListener() {

                        // ��λ�øı�ʱ����
                        @Override
                        public void onLocationChanged(Location location) {
                        	Toast.makeText(
                                    getBaseContext(),
                                    "onLocationChanged", Toast.LENGTH_SHORT).show();
                                updateLocation(location);
                        }

                        // ProviderʧЧʱ����
                        @Override
                        public void onProviderDisabled(String arg0) {
                        	Toast.makeText(
                                    getBaseContext(),
                                    "onProviderDisabled", Toast.LENGTH_SHORT).show();

                        }

                        // Provider����ʱ����
                        @Override
                        public void onProviderEnabled(String arg0) {
                        	Toast.makeText(
                                    getBaseContext(),
                                    "onProviderEnabled", Toast.LENGTH_SHORT).show();
                        }

                        // Provider״̬�ı�ʱ����
                        @Override
                        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                        	Toast.makeText(
                                    getBaseContext(),
                                    "onStatusChanged", Toast.LENGTH_SHORT).show();
                        }
                };

                // 500�������һ�Σ�����λ�ñ仯
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locationListener);
                
        }
        
        private void updateLocation(Location location) {
            if (location != null) {
            	Toast.makeText(
                        getBaseContext(),
                        "get location  ", Toast.LENGTH_SHORT).show();
            	editLocation.setText("��λ������Ϣ���£�" + location.toString() + "\n\t���о��ȣ�" + location.getLongitude() + "\n\t����γ�ȣ�"
                                    + location.getLatitude());
            } else {
            	Toast.makeText(
                        getBaseContext(),
                        "get not location  ", Toast.LENGTH_SHORT).show();
            }
    }
        
        
 
}

package com.example.getgeonameandweather;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

import com.example.getgeonameandweather.bean.Astronomy;
import com.example.getgeonameandweather.bean.Hourly;
import com.example.getgeonameandweather.bean.Weather;

public class LocalWeather extends WwoApi {
	public static final String FREE_API_ENDPOINT = "http://api.worldweatheronline.com/free/v2/weather.ashx";
	public static final String PREMIUM_API_ENDPOINT = "http://api.worldweatheronline.com/premium/v1/premium-weather-V2.ashx";

	LocalWeather(boolean freeAPI) {
		super(freeAPI);
		if(freeAPI) {
			apiEndPoint = FREE_API_ENDPOINT;
		} else {
			apiEndPoint = PREMIUM_API_ENDPOINT;
		}
	}
	
	Weather callAPI(String query) {
		return getLocalWeatherData(getInputStream(apiEndPoint + query));
	}
	
	Weather getLocalWeatherData(InputStream is) {
		Weather weather = null;
		
		try {
			Log.d("WWO", "getLocalWeatherData");
			   
	        XmlPullParser xpp = getXmlPullParser(is);
	        
	        weather = new Weather();
	        weather.setDate(getTextForTag(xpp, "date"));
	        
	        Astronomy astronomy = new Astronomy();
	        astronomy.setSunrise(getTextForTag(xpp, "sunrise"));
	        astronomy.setSunset(getTextForTag(xpp, "sunset"));
	        weather.setAstronomy(astronomy);
	        
	        weather.setDate(getTextForTag(xpp, "date"));
	        weather.setMaxtempC(getTextForTag(xpp, "maxtempC"));
	        weather.setMintempC(getTextForTag(xpp, "mintempC"));
	        
	        List<Hourly> hourlyList = new ArrayList<Hourly>();
			for (int i = 0; i < 8; i++) {
				Hourly hourly = new Hourly();
				hourly.setTime(getTextForTag(xpp, "time"));
				hourly.setTempC(getTextForTag(xpp, "tempC"));
				hourly.setWindspeedKmph(getTextForTag(xpp, "windspeedKmph"));
				hourly.setWinddirDegree(getTextForTag(xpp, "winddirDegree"));
				hourly.setWeatherCode(getTextForTag(xpp, "weatherCode"));
				hourly.setPressure(getTextForTag(xpp, "pressure"));
				hourly.setCloudcover(getTextForTag(xpp, "cloudcover"));

				hourlyList.add(hourly);
			}
			weather.setHourlyList(hourlyList);
	        
		} catch (Exception e) {
			Log.i("", "" + e);
		}
	
		return weather;
	}
	
	class Params extends RootParams {
		String q;					//required
		String extra;
		String num_of_days="1";		//required
		String date;
		String cc;					//default "yes"
		String includeLocation;		//default "no"
		String format;				//default "xml"
		String show_comments="no";
		String callback;
		String key;					//required
		
		Params(String key) {
			this.key = key;
		}
		
		Params setQ(String q) {
			this.q = q;
			return this;
		}
		
		Params setExtra(String extra) {
			this.extra = extra;
			return this;
		}
		
		Params setNumOfDays(String num_of_days) {
			this.num_of_days = num_of_days;
			return this;
		}
		
		Params setDate(String date) {
			this.date = date;
			return this;
		}

		
		Params setCc(String cc) {
			this.cc = cc;
			return this;
		}
		
		Params setIncludeLocation(String includeLocation) {
			this.includeLocation = includeLocation;
			return this;
		}
		
		Params setFormat(String format) {
			this.format = format;
			return this;
		}
		
		Params setShowComments(String showComments) {
			this.show_comments = showComments;
			return this;
		}
		
		Params setCallback(String callback) {
			this.callback = callback;
			return this;
		}
		
		Params setKey(String key) {
			this.key = key;
			return this;
		}
	}
}


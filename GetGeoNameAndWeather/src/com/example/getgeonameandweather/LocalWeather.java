package com.example.getgeonameandweather;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

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
	
	WeatherData callAPI(String query) {
		return getLocalWeatherData(getInputStream(apiEndPoint + query));
	}
	
	WeatherData getLocalWeatherData(InputStream is) {
		WeatherData data = null;
		
		try {
			Log.d("WWO", "getLocalWeatherData");
			   
	        XmlPullParser xpp = getXmlPullParser(is);
	        
	        data = new WeatherData();
	        Weather weather = new Weather();
	        Astronomy astronomy = new Astronomy();
	        weather.astronomy = astronomy;
	        List<Hourly> hourlyList = new ArrayList<Hourly>();
	        weather.hourlyList = hourlyList;
	        data.weather = weather;

	        weather.date = getTextForTag(xpp, "date");
	        weather.astronomy.sunrise = getTextForTag(xpp, "sunrise");
	        weather.astronomy.sunset = getTextForTag(xpp, "sunset");
	        weather.maxtempC = getTextForTag(xpp, "maxtempC");
	        weather.mintempC = getTextForTag(xpp, "mintempC");
	        
			for (int i = 0; i < 8; i++) {
				Hourly hourly = new Hourly();
				hourly.time = getTextForTag(xpp, "time");
				hourly.tempC = getTextForTag(xpp, "tempC");
				hourly.windspeedKmph = getTextForTag(xpp, "windspeedKmph");
				hourly.winddirDegree = getTextForTag(xpp, "winddirDegree");
				hourly.weatherCode = getTextForTag(xpp, "weatherCode");
				hourly.pressure = getTextForTag(xpp, "pressure");
				hourly.cloudcover = getTextForTag(xpp, "cloudcover");

				hourlyList.add(hourly);
			}
	        
		} catch (Exception e) {
			Log.i("", "" + e);
		}
	
		return data;
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
	
	class WeatherData {
		Request request;
		Weather weather;
	}
	
	class Request {
		String type;
		String query;
	}

//	class CurrentCondition {
//	    String observation_time;
//	    String temp_C;
//	    String weatherCode;
//	    String weatherIconUrl;
//	    String weatherDesc;
//	    String windspeedMiles;
//	    String windspeedKmph;
//	    String winddirDegree;
//	    String winddir16Point;
//	    String precipMM;
//	    String humidity;
//	    String visibility;
//	    String pressure;
//	    String cloudcover;
//	}

	class Weather {
	    String date;
	    Astronomy astronomy;
	    List<Hourly> hourlyList;
	    String maxtempC;
	    String mintempC;
//	    String tempMaxC;
//	    String tempMaxF;
//	    String tempMinC;
//	    String tempMinF;
//	    String windspeedMiles;
//	    String windspeedKmph;
//	    String winddirection;
//	    String weatherCode;
//	    String weatherIconUrl;
//	    String weatherDesc;
//	    String precipMM;
	}
	
	class Astronomy {
		String sunrise;
		String sunset;
	}
	
	class Hourly {
		String time;
		String tempC;
		String windspeedKmph;
		String winddirDegree;
		String pressure;
		String cloudcover;
		String weatherCode;
		String weatherName;
	}
}


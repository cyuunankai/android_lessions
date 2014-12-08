package com.example.getgeonameandweather;

import com.example.getgeonameandweather.bean.LocationData;
import com.example.getgeonameandweather.bean.Weather;

public class WeatherAndLocation {

	private Weather weatherData;
	private LocationData locationData;
	public Weather getWeatherData() {
		return weatherData;
	}
	public void setWeatherData(Weather weatherData) {
		this.weatherData = weatherData;
	}
	public LocationData getLocationData() {
		return locationData;
	}
	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}
}

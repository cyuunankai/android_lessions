package com.example.getgeonameandweather;

import com.example.getgeonameandweather.LocalWeather.WeatherData;
import com.example.getgeonameandweather.LocationSearch.LocationData;

public class WeatherAndLocation {

	private WeatherData weatherData;
	private LocationData locationData;
	public WeatherData getWeatherData() {
		return weatherData;
	}
	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}
	public LocationData getLocationData() {
		return locationData;
	}
	public void setLocationData(LocationData locationData) {
		this.locationData = locationData;
	}
}

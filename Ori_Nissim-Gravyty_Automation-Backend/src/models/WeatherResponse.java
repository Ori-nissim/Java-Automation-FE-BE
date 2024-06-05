package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is the main class that the JSON is mapped to. It consists of subclasses
 * according to the JSON format, where some fields are ignored because of the
 * usage in the exercise is limited to specific fields
 */

public class WeatherResponse {

	// Fields to represent the JSON structure in the query
	@JsonIgnore
	private Coord coord;
	private List<Weather> weather;
	@JsonIgnore
	private String base;
	// Holds temp value
	private MainData main;
	private Long visibility;
	@JsonIgnore
	private Wind wind;
	@JsonIgnore
	private Clouds clouds;
	private Long dt;
	// Holds country value
	private Sys sys;
	private Long timezone;
	private Long id;
	private String name;
	private Integer cod;

	public void printTemperature(String unitType) {
		System.out.println("The temperature in " + this.getName() + " is " + this.getTemperature() + " " + unitType);
	}

	// Setters
	
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void setMain(MainData main) {
		this.main = main;
	}

	public void setVisibility(Long visibility) {
		this.visibility = visibility;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public void setDt(Long dt) {
		this.dt = dt;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public void setTimezone(Long timezone) {
		this.timezone = timezone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	// Getters 
	
	public List<Weather> getWeather() {
		return weather;
	}

	public String getBase() {
		return base;
	}

	public MainData getMain() {
		return main;
	}

	public Long getVisibility() {
		return visibility;
	}

	public Wind getWind() {
		return wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public Long getDt() {
		return dt;
	}

	public Sys getSys() {
		return sys;
	}

	public Long getTimezone() {
		return timezone;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public Integer getCod() {
		return cod;
	}

	public String getCountryName() {
		return sys.getCountry(); // sys class stores the country
	}

	public Double getTemperature() {
		return main.getTemp(); // main class stores the temperature
	}

	public Coord getCoord() {
		return coord;
	}

}

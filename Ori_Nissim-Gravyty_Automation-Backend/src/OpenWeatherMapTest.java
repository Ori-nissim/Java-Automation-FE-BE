
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.WeatherResponse;

public class OpenWeatherMapTest {

	// Enum that represents available temperature units to query the API
	private enum OpenWeatherTemperatureUnits {
		CELSIUS("metric"), FARENHEIT("imperial"), KELVIN("standard");

		private final String value;

		OpenWeatherTemperatureUnits(String string) {
			this.value = string;
		}
	}

	// Given API key
	private static final String API_KEY = "a6919dd2e20c5ca67bcf1c727a0b36bf";

	// Maps the JSON to a WeatherResponse object
	private static ObjectMapper mapper = new ObjectMapper();

	// HTTP requests client
	private static final HttpClient client = HttpClient.newHttpClient();

	public static void main(String[] args) throws IOException, InterruptedException {

		// Fetches weather data for the specified city and stores the response.
		HttpResponse<String> londonResponse = getCityWeather("London", OpenWeatherTemperatureUnits.FARENHEIT);
		// Store data in an object
		WeatherResponse londonWeatherData = mapper.readValue(londonResponse.body(), WeatherResponse.class);

		// 1. Print response code
		printResponseCode(londonResponse);

		// 2. Print response body
		printResponseBody(londonResponse);

		// 3. Verify that the response code is 200
		// We don't need to actually call the test method, just run the application as TestNG test
		
		// 4. Gets the country ("country": "GB")
		System.out.println("Country is: " + londonWeatherData.getCountryName());

		// 5. Print the temperature in Tel-Aviv in Celsius, London and NY in Fahrenheit
		fetchDataPrintTemperature("Tel aviv", OpenWeatherTemperatureUnits.CELSIUS);
		fetchDataPrintTemperature("London", OpenWeatherTemperatureUnits.FARENHEIT);
		fetchDataPrintTemperature("New york", OpenWeatherTemperatureUnits.FARENHEIT);

	}

	/**
	 * @brief Prints the temperature in the specific city with the given temperature
	 *        unit type
	 * 
	 * @param cityName represented the desired city to query
	 * @param unitType The unit type of the temperature can be Kelvin, Celsius or Fahrenheit.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void fetchDataPrintTemperature(String cityName, OpenWeatherTemperatureUnits unitType)
			throws IOException, InterruptedException {

		HttpResponse<String> response = getCityWeather(cityName, unitType);
		WeatherResponse weatherResponse = mapper.readValue(response.body(), WeatherResponse.class);

		weatherResponse.printTemperature(unitType.value);
	}

	/**
	 * @brief Prints the status code of the provided HTTP response
	 * 
	 * @param response The HttpResponse object containing the response data
	 */
	private static void printResponseCode(HttpResponse<String> response) {

		System.out.println("Status code: " + response.statusCode());
	}

	/**
	 * @brief Prints the body of the provided HTTP response.
	 * 
	 * @param response The HttpResponse object containing the response data.
	 */
	private static void printResponseBody(HttpResponse<String> response) {
		System.out.println("Response body:\n" + response.body());
	}

	/**
	 * @brief Fetches weather data for the specified city from the OpenWeatherMap
	 *        API
	 * 
	 * @param cityName The name of the city to get weather data for.
	 * @param unitType The unit type of the temperature can be Kelvin, Celsius or Fahrenheit.
	 * @return An HttpResponse object containing the response data from the API.
	 * @throws IOException          If there's an error making the HTTP request.
	 * @throws InterruptedException If the HTTP request gets interrupted.
	 */
	private static HttpResponse<String> getCityWeather(String cityName, OpenWeatherTemperatureUnits unitType)
			throws IOException, InterruptedException {

		// Ensure that the are no missing fields
		if (cityName == null || cityName.isEmpty()) {
			throw new IllegalArgumentException("City name cannot be null or empty");
		}
		if (unitType == null) {
			throw new IllegalArgumentException("Unit type cannot be null");
		}

		// Replace white spaces with %20 so it can be passed in the URL
		String encodedCityName = cityName.replaceAll(" ", "%20");
		String url = buildUrl(encodedCityName, unitType);
		HttpRequest request = HttpRequest.newBuilder().GET().uri(java.net.URI.create(url)).build();

		try {
			return client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// Throw the error to the caller
			throw e;
		}
	}

	/**
	 * @brief Builds the URL for the OpenWeatherMap API request based on the city name and API key
	 * 
	 * @param city The name of the city to include in the URL.
	 * @return The complete URL string for the API request.
	 */
	private static String buildUrl(String city, OpenWeatherTemperatureUnits unitType) {
		return String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&APPID=%s", city,
				unitType.value, API_KEY);
	}

	/**
	 * @brief Send a request according to the parameters and check the status code,
	 *        then maps to an object
	 * 
	 * @param cityName The name of the city to include in the URL
	 * @param unitType The unit type of the temperature can be Kelvin, Celsius or
	 *                 Fahrenheit.
	 * @return The complete URL string for the API request
	 */
	private void mainTest(String cityName, OpenWeatherMapTest.OpenWeatherTemperatureUnits unitType)
			throws IOException, InterruptedException {

		HttpResponse<String> response = getCityWeather(cityName, unitType);
		// Assert that the status code is equal to the desired code
		assertEquals(response.statusCode(), 200);

		WeatherResponse weatherResponse = mapper.readValue(response.body(), WeatherResponse.class);
		weatherResponse.printTemperature(unitType.value);
	}

	// Tests

	@Test(description = "Tests the response status code is 200 OK")
	public static void testStatusCodeIs200() throws IOException, InterruptedException {

		// Fetches weather data
		HttpResponse<String> response;
		response = getCityWeather("London", OpenWeatherTemperatureUnits.CELSIUS);

		// Assert that the status code is equal to the desired code
		assertEquals(response.statusCode(), 200);

	}
	@Test(description = "Tests the response status code is not 200 OK, because of bad request")
	public static void testStatusCodeNot200() throws IOException, InterruptedException {

		// Fetches weather data
		HttpResponse<String> response;
		response = getCityWeather("just a random sentence", OpenWeatherTemperatureUnits.CELSIUS);

		// Assert that the status code is equal to the desired code
		assertNotEquals(response.statusCode(), 200);

	}

	@Test(description = "tests a request to London in farenheit")
	public void testLondonWeatherInFahrenheit() throws IOException, InterruptedException {
		OpenWeatherTemperatureUnits unitType = OpenWeatherTemperatureUnits.FARENHEIT;
		String cityName = "London";
		mainTest(cityName, unitType);
	}

	@Test
	public void testTelAvivWeatherInCelsius() throws IOException, InterruptedException {

		String cityName = "Tel Aviv";
		OpenWeatherTemperatureUnits unitType = OpenWeatherTemperatureUnits.CELSIUS;
		mainTest(cityName, unitType);

	}

	@Test
	public void testNewYorkWeatherInFahrenheit() throws IOException, InterruptedException {

		String cityName = "New York";
		OpenWeatherTemperatureUnits unitType = OpenWeatherTemperatureUnits.FARENHEIT;
		mainTest(cityName, unitType);

	}

	@Test
	public void testTelAvivCountry() throws IOException, InterruptedException {
		HttpResponse<String> response = getCityWeather("Tel Aviv", OpenWeatherTemperatureUnits.CELSIUS);

		// Assert that the status code is equal to the desired code
		assertEquals(response.statusCode(), 200);

		WeatherResponse telAvivWeatherData = mapper.readValue(response.body(), WeatherResponse.class);
		assertEquals(telAvivWeatherData.getCountryName(), "IL");
	}

}

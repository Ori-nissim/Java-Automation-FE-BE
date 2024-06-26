import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.ConnectionData;
import models.LinkedInUser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class tests the whole process of getting data from LinkedIn to JSON,
 * While separating each part to a particular test.
 */

public class main {

	// Constants
	private static final String CHROMEDRIVER_PATH = "C:\\Users\\Ori Nissim\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
	private static final String LINKEDIN_URL = "https://www.linkedin.com/home";
	private static final String USERNAME = "oria8376@gmail.com";
	private static final String PASSWORD = "123456Ori";

	// Shared global objects required for the tests
	private static LinkedInUser user = new LinkedInUser();
	private static ArrayList<ConnectionData> connectionDataList = new ArrayList<ConnectionData>();
	private static WebDriver driver;

	@Test(description = "Initialize chromedriver", priority = 0)
	private static void testInitializeChromedriver() {

		// Set the system property for the Chrome driver
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
		driver = new ChromeDriver();
	}

	@Test(description = "Navigate to linkedin", priority = 1)
	private static void testNavigateToLinkedIn() throws InterruptedException {

		// Navigate to LinkedIn homepage
		driver.get(LINKEDIN_URL);
		Thread.sleep(3000); // wait for the browser to open
	}

	@Test(description = "Navigate to sign in and perform login", priority = 2)
	public static void testSigninToLinkedIn() throws InterruptedException {

		// Find the button using css selector
		WebElement signInButton = driver
				.findElement(By.cssSelector(Selectors.SIGN_IN_BUTTON_SELECTOR));
		signInButton.click();

		WebElement usernameField = driver.findElement(By.id(Selectors.USERNAME_FIELD_SELECTOR));
		usernameField.sendKeys(USERNAME);

		WebElement passwordField = driver.findElement(By.id(Selectors.PASSWORD_FIELD_SELECTOR));
		passwordField.sendKeys(PASSWORD);

		// Find the button using css selector
		WebElement submitButton = driver.findElement(By.cssSelector(Selectors.SUBMIT_LOGIN_BUTTON_SELECTOR));
		submitButton.click();

	}

	@Test(description = "Open profile and extract data", priority = 3)
	public static void testGetDataFromProfile() throws InterruptedException {
		// Wait for the browser to load
		Thread.sleep(2000);
		WebElement profilePicture = driver.findElement(By.xpath(Selectors.PROFILE_PIC_SELECTOR));
		profilePicture.click();
		
		Thread.sleep(2000); // wait for the browser to open
		WebElement viewProfile = driver.findElement(By.linkText(Selectors.VIEW_PROFILE_LINK_SELECTOR));
		viewProfile.click();

		// Wait for the browser to load
		Thread.sleep(2000);

		WebElement nameElement = driver
				.findElement(By.cssSelector(Selectors.PROFILE_NAME_SELECTOR));
		user.setName(nameElement.getText());

		WebElement cityElement = driver
				.findElement(By.cssSelector(Selectors.PROFILE_CITY_SELECTOR));
		user.setCity(cityElement.getText());

		WebElement experienceSection = driver
				.findElement(By.cssSelector(Selectors.EXPERIENCE_SECTION_SELECTOR));
		List<WebElement> companyElements = experienceSection.findElements(By.tagName("li"));
		WebElement workplaceElement = companyElements.get(0).findElement(By.xpath(
				Selectors.WORKPLACE_ELEMENT_SELECTOR));
		user.setWorkplace(workplaceElement.getText());

		// wait for the browser to load
		Thread.sleep(3000);
	}

	@Test(description = "Navigate to connections page and get the first page data, then print it as JSON", priority = 4)
	public static void testGetConnectionsData() throws InterruptedException, JsonProcessingException {

		// Go to connections page
		WebElement myNetwork = driver.findElement(By.xpath(Selectors.MY_NETWORK_BUTTON_SELECTOR));
		myNetwork.click();

		// Wait for the browser to load
		Thread.sleep(3000);

		// Go to connections list
		WebElement myConnectionsButton = driver
				.findElement(By.cssSelector(Selectors.CONNECTIONS_BUTTON_SELECTOR));
		myConnectionsButton.click();

		// Wait for the browser to load
		Thread.sleep(3000);

		// Get connection list
		List<WebElement> connectionList = driver.findElements(By.className(Selectors.CONNECTION_LIST_SELECTOR));

		if (connectionList.isEmpty())
			throw new NullPointerException("List is empty, No Connections to this user!");

		Thread.sleep(1000);

		for (WebElement listItem : connectionList) {
			ConnectionData current = getListItemAsConnectionData(listItem);
			if (current != null)
				connectionDataList.add(current);
		}

		// Print the scraped data as a formatted JSON string with indentation
		String jsonData = getDataAsJSONString(user, connectionDataList);
		System.out.println(jsonData);

	}

	/**
	 * @brief Extracts connection data from a list of WebElement on a LinkedIn
	 *        profile.
	 *
	 * @param listItem (WebElement): The WebElement representing the list item
	 *                 containing the connection data.
	 * @return ConnectionData: A populated ConnectionData object containing the
	 *         extracted information (full name, occupation, connection duration),
	 *         or null if any element is not found.
	 */
	private static ConnectionData getListItemAsConnectionData(WebElement listItem) {

		ConnectionData connectionData = new ConnectionData();
		try {
			// Get name
			WebElement connectionName = listItem
					.findElement(By.cssSelector(Selectors.CONNECTION_NAME_SELECTOR));
			connectionData.setFullName(connectionName.getText());
			// Get occupation
			WebElement connectionOccupation = listItem
					.findElement(By.cssSelector(Selectors.CONNECTION_OCCUPATION_SELECTOR));
			connectionData.setOccupation(connectionOccupation.getText());
			// Get connection time
			WebElement connectionTime = listItem
					.findElement(By.cssSelector(Selectors.CONNECTION_DURATION_SELECTOR));
			connectionData.setConnectionDuration(connectionTime.getText());
		} catch (NoSuchElementException e) {
			System.err.println("Error finding elements for connection data: " + e.getMessage());
			return null;
		}

		return connectionData;
	}

	/**
	 * @brief Converts a LinkedInUser object and a list of ConnectionData objects
	 *        into a well-formatted JSON string.
	 * 
	 *        This function takes a LinkedInUser object containing user information
	 *        and a list of ConnectionData objects representing connections from a
	 *        LinkedIn profile. It then uses Jackson to serialize this data into a
	 *        formatted JSON string. The JSON string includes the user's name,
	 *        workplace, city, and a list of connections with their names,
	 *        occupations, and connection durations.
	 *
	 * @param linkedInUser (LinkedInUser): An object containing the user's
	 *                     information like name, workplace, and city.
	 * @param connections  (List<ConnectionData>): A list containing connection data
	 *                     objects extracted from a LinkedIn profile.
	 * @return (String): A well-formatted JSON string representing the user's
	 *         profile and connection data.
	 * @throws JsonProcessingException: If there's an error during JSON
	 *                                  serialization using Jackson.
	 */
	private static String getDataAsJSONString(LinkedInUser linkedInUser, List<ConnectionData> connections)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		JSONObject jsonData = new JSONObject();

		jsonData.put("myName", linkedInUser.getName());
		jsonData.put("myWorkplace", linkedInUser.getWorkplace());
		jsonData.put("city", linkedInUser.getCity());

		jsonData.put("connections", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(connections));

		// Return the string clean
		return jsonData.toString(2).replaceAll("\\\\r\\\\n", "\n").replaceAll("\\\\", "");
	}
}

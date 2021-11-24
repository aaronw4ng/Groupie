package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.cucumber.java.eo.Se;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	// !Remember to use https for all url
	private static final String ROOT_URL = "https://localhost:8080/";

	private int USERNAME_LENGTH = 10;
	private String USERNAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static String generatedUsername = null;

	private ChromeOptions handlingSSL = new ChromeOptions();
	private WebDriver driver;

	@Before
	public void before() {
		handlingSSL.setAcceptInsecureCerts(true);
		driver = new ChromeDriver(handlingSSL);
		if (generatedUsername == null) {
			generatedUsername = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		}
	}

	// *** LOGIN PAGE ***

	@Given ("user is on the Login page")
	public void user_is_on_the_Login_page() {
		driver.get(ROOT_URL);
	}

	@Then("user should be logged in")
	public void user_should_be_logged_in() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertTrue(alert != null);
	}

	// User over attempt limit
	@Then("the {string} button should be disabled")
	public void the_button_should_be_disabled(String string) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean isEnabled = driver.findElement(By.id(string)).isEnabled();
		// System.out.println(driver.findElement(By.id(string)).isEnabled());
		assertTrue(isEnabled == false);
	}

	// *** CREATE ACCOUNT PAGE ***

	@Given("user is on the Create User page")
	public void user_is_on_the_Create_User_page() {
		driver.get("https://localhost:8080/pages/create-account.jsp");
	}

	@When("user re-types {string}")
	public void user_retypes_password(String string) {
		driver.findElement(By.id("re-input-password")).sendKeys(string);
	}

	@Then("new account should be created")
	public void new_account_should_be_created() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertTrue(alert != null);
	}

	// *** GENERAL ACCOUNT CREATION TESTS ***

	// Whens
	@When("user inputs in username")
	public void user_inputs_username() {
		driver.findElement(By.id("input-username")).sendKeys(generatedUsername);
	}

	@When("user inputs {string} in password")
	public void user_inputs_password(String string) {
		driver.findElement(By.id("input-password")).sendKeys(string);
	}

	@When("user clicks {string} button")
	public void user_clicks_button(String string) {
		driver.findElement(By.id(string)).click();
	}

	@When("user accepts the alert")
	public void user_accepts_the_alert() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Thens

	@Then("user should be shown {string} error message")
	public void user_should_be_shown_error_message(String string) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] warnings = string.split(",");
		for (String names : warnings) {
			WebElement element = driver.findElement(By.className("warning-message")).findElement(By.name(names));
			assertTrue(element != null);
		}
	}

	@Then("user should be on Login page")
	public void user_should_be_on_Login_page() {
		String currURL = driver.getCurrentUrl();
		assertEquals("https://localhost:8080/index.jsp", currURL);
	}

	@Then("user should be on Create Account page")
	public void user_should_be_on_Create_Account_page() {
		String currURL = driver.getCurrentUrl();
		assertEquals("https://localhost:8080/pages/create-account.jsp", currURL);
	}

	// *** CREATE PROPOSAL PAGE ***

	@Then("user should see an error alert")
	public void user_should_see_an_error_alert() {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertTrue(alert != null);
	}

	// *** SEARCH EVENTS ***
	@When("user inputs {string} in event search")
	public void user_inputs_in_event_search(String string) {
		WebElement queryBox = driver.findElement(By.id("event-search-input"));
		queryBox.sendKeys(string);
	}

	@When("user selects {string} in start date")
	public void user_selects_in_start_date(String string) {
		WebElement queryBox = driver.findElement(By.id("start"));
		queryBox.sendKeys(string);
	}

	@When("user selects {string} in end date")
	public void user_selects_in_end_date(String string) {
		WebElement queryBox = driver.findElement(By.id("end"));
		queryBox.sendKeys(string);
	}

	@When("user inputs {string} in city")
	public void user_inputs_in_city(String string) {
		WebElement queryBox = driver.findElement(By.id("event-city-input"));
		queryBox.sendKeys(string);
	}

	@When("user inputs {string} in zipcode")
	public void user_inputs_in_zipcode(String string) {
		WebElement queryBox = driver.findElement(By.id("event-zip-input"));
		queryBox.sendKeys(string);
	}

	@When("user selects {string} in genre")
	public void user_selects_in_genre(String string) {
		// Write code here that turns the phrase above into concrete actions
		Select genre = new Select(driver.findElement(By.id("event-genre-input")));
		genre.selectByValue(string);
	}

	@Then("user should see {string}")
	public void user_should_see_event_results(String string) {
		// Write code here that turns the phrase above into concrete actions
		String result = driver.findElement(By.id("event-search-result-count")).getText();
		// TODO: finish up assertion
		//assertTrue(result.getText().equalsIgnoreCase(string));

	}

	@Then("user should see {string} event name")
	public void user_should_see_event_name(String string) {
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.findElement(By.id("result-title-id-0")).getText();
		assertTrue(result.equalsIgnoreCase(string));

	}

	@Then("user should see events after {string}")
	public void user_should_see_events_after(String string) {
		// Write code here that turns the phrase above into concrete actions
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// parse the date into correct format
		String[] dateArr = string.split("-");
		String yearString = dateArr[0];
		String dateInputString = yearString + "-" + dateArr[1] + "-" + dateArr[2] + "T00:00:00Z";
		// Get the date of first event result
		String eventDateString = driver.findElement(By.id("start-date-id-0")).getText();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date startDate;
		Date eventDate;

		// convert string into dates
		try{
			startDate = df.parse(dateInputString);
			eventDate = df.parse(eventDateString);
			// make sure event date is after start date
			assertTrue(eventDate.after(startDate));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	@Then("user should see events before {string}")
	public void user_should_see_events_before(String string) {
		// Write code here that turns the phrase above into concrete actions
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// parse the date into correct format
		String[] dateArr = string.split("-");
		String yearString = dateArr[0];
		String dateInputString = yearString + "-" + dateArr[1] + "-" + dateArr[2] + "T00:00:00Z";
		// Get the date of first event result
		String eventDateString = driver.findElement(By.id("start-date-id-0")).getText();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date endDate;
		Date eventDate;

		// convert string into dates
		try{
			endDate = df.parse(dateInputString);
			eventDate = df.parse(eventDateString);
			// make sure event date is before end date
			assertTrue(eventDate.before(endDate));
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}

	@Then("user should see events located in city {string}")
	public void user_should_see_events_located_in_city(String string) {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("user should see events located in zipcode {string}")
	public void user_should_see_events_located_in_zipcode(String string) {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("user should see events related to {string}")
	public void user_should_see_events_related_to(String string) {
		// Write code here that turns the phrase above into concrete actions

	}

	@After()
	public void after() {
		driver.quit();
	}
}

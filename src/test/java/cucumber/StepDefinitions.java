package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;

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

	@After()
	public void after() {
		driver.quit();
	}
}

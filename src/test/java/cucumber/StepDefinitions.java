package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertFalse;
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

	private int USERNAME_LENGTH = 20;
	private String USERNAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static String generatedUsername = null;
	private static String generatedUsername2 = null;
	private static String generatedUsername3 = null;


	private String newUser1 = null;
	private String newUser2 = null;
	private String newUser3 = null;

	// usernames are for removing event
	private static String randomUser1 = null;
	private static String randomUser2 = null;
	private static String randomUser3 = null;
  

	private ChromeOptions handlingSSL = new ChromeOptions();
	private WebDriver driver;

	@Before
	public void before() {
		handlingSSL.setAcceptInsecureCerts(true);
		driver = new ChromeDriver(handlingSSL);
		if (generatedUsername == null) {
			generatedUsername = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		}
		if (generatedUsername2 == null) {
			generatedUsername2 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		}
		if (generatedUsername3 == null) {
			generatedUsername3 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		}
	}

	/* Terry's Acceptance Tests Tool Kit */
	private void refreshNewUserNames() {
		newUser1 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		newUser2 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		newUser3 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
	}

	// overloaded method for inputing username
	private void user_inputs_username(String username) {
		driver.findElement(By.id("input-username")).sendKeys(username);
	}

	private void registerNewUser(String username){
		user_is_on_the_Create_User_page();
		user_inputs_username(username);
		user_inputs_password(username);
		user_retypes_password(username);
		user_clicks_button("btn-create-account");
		user_accepts_the_alert();
		userLogsOut();
	}
	
	@Given ("a new user is set up")
	public void setUpOneNewUser() {
		refreshNewUserNames();
		registerNewUser(newUser1);
	}

	@Given ("two new users are set up")
	public void setUpTwoNewUsers() {
		refreshNewUserNames();
		registerNewUser(newUser1);
		registerNewUser(newUser2);
	}

	@Given ("three new users are set up")
	public void setUpThreeNewUsers() {
		refreshNewUserNames();
		registerNewUser(newUser1);
		registerNewUser(newUser2);
		registerNewUser(newUser3);
	}

	private void newUserLogsIn(String username) {
		user_is_on_the_Login_page();
		user_inputs_username(username);
		user_inputs_password(username);
		user_clicks_button("btn-login");
		user_accepts_the_alert();
	}

	@Given ("new user one logs in")
	public void newUserOneLogsIn() {
		newUserLogsIn(newUser1);
	}

	@Given ("new user two logs in")
	public void newUserTwoLogsIn() {
		newUserLogsIn(newUser2);
	}

	@Given ("new user three logs in")
	public void newUserThreeLogsIn() {
		newUserLogsIn(newUser3);
	}

	@When ("wait for {float} seconds")
	public void waitForSeconds(float seconds) {
		try {
			Thread.sleep((long)(seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
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
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean isEnabled = driver.findElement(By.id(string)).isEnabled();
		System.out.println(driver.findElement(By.id(string)).isEnabled());
		assertTrue(isEnabled == false);
	}

	@When("user waits for login cool down period")
	public void userWaitsForLoginCoolDownPeriod() {
		// wait for 60+ seconds to try again
		try {
			Thread.sleep(60500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	@When("user inputs in second username")
	public void userInputsInSecondUsername() {
		driver.findElement(By.id("input-username")).sendKeys(generatedUsername2);
	}

	@When("user inputs in third username")
	public void userInputsInThirdUsername() {
		driver.findElement(By.id("input-username")).sendKeys(generatedUsername3);
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
	@When("user inputs {string} in proposal name")
	public void user_inputs_in_proposal_name(String string) {
		WebElement queryBox = driver.findElement(By.id("input-proposal-name"));
		// clear the placeholder value
		queryBox.clear();
		queryBox.sendKeys(string);
		// Click outside of selection 
		driver.findElement(By.id("proposal-name-header")).click();
	}

	@When("user adds first user result")
	public void user_adds_first_user_result() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("user-card-1")).click();
	}

	@When("user adds first event result")
	public void user_adds_first_event_result() {
		waitForSeconds(4);
		driver.findElement(By.id("btn-add-result-id-0")).click();
	}

	@Then("user should see an error alert")
	public void user_should_see_an_error_alert() {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertTrue(alert != null);
	}

	@Then("user should have successfully sent proposal")
	public void user_should_have_successfully_sent_proposal() {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Proposal sent successfully!", alert.getText());
	}


	@Then("user should have successfully saved proposal")
	public void userShouldHaveSuccessfullySavedProposal() {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Draft successfully saved!", alert.getText());
	}

	@Then("user should see an error alert message {string}")
	public void userShouldSeeAnErrorAlertMessage(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals(string, alert.getText());
	}


	// *** SEARCH EVENTS ***
	@When("user inputs {string} in event search")
	public void user_inputs_in_event_search(String string) {
		WebElement queryBox = driver.findElement(By.id("event-search-input"));
		queryBox.sendKeys(string);
		waitForSeconds(1);
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
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals(string, alert.getText());
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
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// parse the date into correct format
		String[] dateArr = string.split("-");
		String yearString = dateArr[2]; // MM-DD-YYYY
		String dateInputString = yearString + "-" + dateArr[0] + "-" + dateArr[1] + "T00:00:00Z";
		// Get the date of first event result
		String eventDateString = driver.findElement(By.id("start-date-id-0")).getText();
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date eventDate;

		// convert string into dates
		try{
			startDate = df1.parse(dateInputString);
			eventDate = df2.parse(eventDateString);
			// make sure event date is after start date
			assertTrue(eventDate.after(startDate));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	@Then("user should see events before {string}")
	public void user_should_see_events_before(String string) {
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// parse the date into correct format
		String[] dateArr = string.split("-");
		String yearString = dateArr[2]; // MM-DD-YYYY
		String dateInputString = yearString + "-" + dateArr[0] + "-" + dateArr[1] + "T00:00:00Z";
		// Get the date of first event result
		String eventDateString = driver.findElement(By.id("start-date-id-0")).getText();
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate;
		Date eventDate;

		// convert string into dates
		try{
			endDate = df1.parse(dateInputString);
			eventDate = df2.parse(eventDateString);
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
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.findElement(By.id("city-id-0")).getText();
		assertTrue(result.equalsIgnoreCase(string));

	}

	@Then("user should see events located in zipcode {string}")
	public void user_should_see_events_located_in_zipcode(String string) {
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.findElement(By.id("zipcode-id-0")).getText();
		System.out.println("zipcode: " + result);
		assertTrue(result.equalsIgnoreCase(string));
	}

	@Then("user should see events related to {string}")
	public void user_should_see_events_related_to(String string) {
		// Write code here that turns the phrase above into concrete actions
		// buffer time for event search to give back results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.findElement(By.id("genre-id-0")).getText();
		assertTrue(result.equalsIgnoreCase(string));
	}

	/* For Automatic Logout When User Inactive */
	@When("user does nothing for over 60s")
	public void userDoesNothingForOver60s() {
		// Wait until pop up shows
		WebElement element = (new WebDriverWait(driver, 65000))
				.until(ExpectedConditions.elementToBeClickable(By.id("stayButton")));
	}

	@Then("auto log out pop up shows")
	public void autoLogOutPopUpShows() {
		assertEquals("Stay", driver.findElement(By.id("stayButton")).getText());
	}

	/* Restrict access to pages to only logged in users */
	@When("user manually types in the create proposal page")
	public void userManuallyTypesInTheCreateProposalPage() {
		driver.get("https://localhost:8080/pages/create-proposal.jsp");
	}

	@Then("user should be redirected to login page")
	public void userShouldBeRedirectedToLoginPage() {
		String currURL = driver.getCurrentUrl();
		assertEquals("https://localhost:8080/index.jsp", currURL);
	}

	@Then("user manually types in the event search page")
	public void userManuallyTypesInTheEventSearchPage() {
		driver.get("https://localhost:8080/pages/event-search.jsp");
	}

	@Then("user manually types in the proposal details page")
	public void userManuallyTypesInTheProposalDetailsPage() {
		driver.get("https://localhost:8080/pages/proposal-details.jsp");
	}

	/* DELETE PROPOSAL */
	@When("user navigates to View Proposals page")
	public void user_navigates_to_View_Proposals_page() {
		driver.findElement(By.id("view-proposals-btn")).click();
		// give buffer time for proposals to show up
		waitForSeconds(2);
	}

	@When("user clicks the first owned proposal")
	public void user_clicks_the_first_owned_proposal() {
		// TODO: click first owned proposal on view proposals page
	}

	@When("user deletes all users")
	public void user_deletes_all_users() {
		// TODO: go through all user cards and click the remove button, and accept the confirmation
	}

	@When("user clicks the delete proposal button")
	public void user_clicks_the_delete_proposal_button() {
		// This is a placeholder function until page is implemented
	}

	@When("user deletes all events")
	public void user_deletes_all_events() {
		// TODO: go through all event cards and click the remove button, and accept the confirmation
	}

	@When("user accepts the confirmation")
	public void user_accepts_the_confirmation() {
		// TODO: await confirmation popup and click 'OK'
	}

	@Then("the proposal should be deleted")
	public void the_proposal_should_be_deleted() {
		// TODO: check url that back on view proposal page
	}

	/* Filter user search when inviting user */
	@When("user types in friend")
	public void userTypesInFriend() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(generatedUsername2);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("user results should be filtered")
	public void userResultsShouldBeFiltered() {
		assertEquals(generatedUsername2.toLowerCase(), driver.findElement(By.id("user-card-1")).getText());
	}

	/* View Sent Proposals */
	@When("user logs out")
	public void userLogsOut() {
		user_clicks_button("logout-btn");
	}

	@When("second user logs in")
	public void secondUserLogsIn() {
		userInputsInSecondUsername();
		user_inputs_password("password1");
	}

	@When("second user navigates to View Proposals page")
	public void secondUserNavigatesToViewProposalsPage() {
		user_navigates_to_View_Proposals_page();
	}

	@Then("user sees their first proposal that they sent out")
	public void userSeesTheirFirstProposalThatTheySentOut() {
		WebElement proposal = driver.findElement(By.id("sent-container-1"));
		assertTrue(proposal != null);
	}

	@Then("user sees the proposal from first user")
	public void secondUserSeesTheProposalFromFirstUser() {
		WebElement proposal = driver.findElement(By.id("received-container-1"));
		assertTrue(proposal != null);
	}

	/* Delete event from proposal at any time */
	@When("two new users are added")
	public void twoNewUsersAreAdded() {
		randomUser1 =  RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		randomUser2 =  RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		driver.findElement(By.id("input-username")).sendKeys(randomUser1);
		user_inputs_password("password1");
		user_retypes_password("password1");
		user_clicks_button("btn-create-account"); // create first user
		user_accepts_the_alert(); // accepts alert
		user_clicks_button("logout-btn"); // first user logs out
		user_is_on_the_Create_User_page(); // go back to create user page
		driver.findElement(By.id("input-username")).sendKeys(randomUser2);
		user_inputs_password("password1");
		user_retypes_password("password1");
		user_clicks_button("btn-create-account"); // create second user
		user_accepts_the_alert(); // accepts alert
		user_clicks_button("logout-btn"); // second user logs out
		user_inputs_username(randomUser1); // first user logs in
		user_inputs_password("password1");
		user_clicks_button("btn-login");
		user_accepts_the_alert(); // accepts alert
	}

	@When("second user inputs {string} in proposal name")
	public void secondUserInputsInProposalName(String arg0) {
		user_inputs_in_proposal_name(arg0);
	}

	@When("user clicks on the first sent proposal")
	public void userClicksOnTheFirstSentProposal() {
		// buffer for time for items to fill in
		// give buffer time for proposals to show up
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("sent-container-1")).click();
	}

	@When("user clicks on the first draft proposal")
	public void userClicksOnTheFirstDraftProposal() {
		// buffer for time for items to fill in
		// give buffer time for proposals to show up
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("draft-container-1")).click();
	}

	@When("user presses button to remove the first event")
	public void userPressesButtonToRemoveTheFirstEvent() {
		// buffer for time for items to fill in
		waitForSeconds(4);
		driver.findElement(By.id("btn-delete-event-0")).click();
	}

	@When("user presses button to remove the first draft event")
	public void userPressesButtonToRemoveTheFirstDraftEvent() {
		// buffer for time for items to fill in
		waitForSeconds(4);
		driver.findElement(By.id("remove-draft-event-0")).click();
	}

	@When("user clicks on {string} proposal")
	public void userClicksOnProposal(String arg0) {

	}

	@When("user presses button to remove the {string} event")
	public void userPressesButtonToRemoveTheEvent(String arg0) {
	}

	@Then("user sees event delete alert")
	public void userSeesEventDeleteAlert() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Are you sure you want to delete this event?", alert.getText());
		user_accepts_the_alert();
	}

	@Then("user sees deleting event will delete proposal alert")
	public void userSeesDeletingEventWillDeleteProposalAlert() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Are you sure you want to delete this event? Doing so will delete the proposal.", alert.getText());
		user_accepts_the_alert();
	}

	@Then("user should see an alert message {string}")
	public void userShouldSeeAnAlertMessage(String arg0) {
	}

	/* Remove invitee from proposal */
	@When("three new users are added")
	public void threeNewUsersAreAdded() {
		randomUser1 =  RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		randomUser2 =  RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		randomUser3 = RandomStringUtils.random(USERNAME_LENGTH, USERNAME_CHARACTERS);
		driver.findElement(By.id("input-username")).sendKeys(randomUser1);
		user_inputs_password("password1");
		user_retypes_password("password1");
		user_clicks_button("btn-create-account"); // create first user
		user_accepts_the_alert(); // accepts alert
		user_clicks_button("logout-btn"); // first user logs out
		user_is_on_the_Create_User_page(); // go back to create user page
		driver.findElement(By.id("input-username")).sendKeys(randomUser2);
		user_inputs_password("password1");
		user_retypes_password("password1");
		user_clicks_button("btn-create-account"); // create second user
		user_accepts_the_alert(); // accepts alert
		user_clicks_button("logout-btn"); // second user logs out
		user_is_on_the_Create_User_page(); // go back to create user page
		driver.findElement(By.id("input-username")).sendKeys(randomUser3);
		user_inputs_password("password1");
		user_retypes_password("password1");
		user_clicks_button("btn-create-account"); // create third user
		user_accepts_the_alert(); // accepts alert
		user_clicks_button("logout-btn"); // third user logs out
		user_inputs_username(randomUser1); // first user logs in
		user_inputs_password("password1");
		user_clicks_button("btn-login");
		user_accepts_the_alert(); // accepts alert
	}
	@When("user adds second user result")
	public void userAddsSecondUserResult() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		waitForSeconds(5);
		driver.findElement(By.id("user-card-2")).click();
	}

	@When("user presses button to remove second user")
	public void userPressesButtonToRemoveSecondUser() {
		// buffer for time for items to fill in
		//String btn = "btn-delete-user-" + newUser2.toLowerCase();
		System.out.println(driver.findElement(By.id(newUser2.toLowerCase())));
		driver.findElement(By.id(newUser2.toLowerCase())).click();
		waitForSeconds(10);
	}

	@When("user presses button to remove second user from draft")
	public void userPressesButtonToRemoveSecondUserFromDraft() {
		// buffer for time for items to fill in
		waitForSeconds(10);
		String btn = "remove-"+newUser2.toLowerCase();
		driver.findElement(By.id(btn)).click();
	}

	@Then("user sees invitee delete alert")
	public void userSeesInviteeDeleteAlert() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Are you sure you want to delete this user?", alert.getText());
		user_accepts_the_alert();
	}

	@Then("user sees delete invitee from draft alert")
	public void userSeesDeleteInviteeFromDraftAlert() {
		// Check for success alert popup
		WebDriverWait wait = new WebDriverWait(driver, 300);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("Are you sure you want to remove this user?", alert.getText());
		user_accepts_the_alert();
	}

	@When("user accepts removing invitee will delete proposal alert")
	public void userAcceptsRemovingInviteeWillDeleteProposalAlert() {
	}

	/* View Draft Proposals */
	@Then("user sees {string} proposal")
	public void userSeesProposal(String arg0) {
	}

	/* Set Unavailability tests */
	@When("user navigates to Privacy Settings page")
	public void userNavigatesToPrivacySettingsPage() {
		System.out.println("user navigates to Privacy Settings page");
		user_clicks_button("privacy-settings-btn");
		waitForSeconds(1);
	}

	@When("user types in {string} in unavailable time box")
	public void userTypesInInUnavailableTimeBox(String arg0) {
		WebElement queryBox = driver.findElement(By.id("hours-unavail"));
		queryBox.sendKeys(arg0);
		waitForSeconds(1);
	}

	@When("user clicks Set Unavailable button")
	public void userClicksSetUnavailableButton() {
		user_clicks_button("unavail-but");
		waitForSeconds(1);
	}

	@When("user clicks Set Available button")
	public void userClicksSetAvailableButton() {
		user_clicks_button("avail-but");
		waitForSeconds(1);
	}

	@When("user should see themselve unavailable until indefinitely")
	public void userShouldSeeThemselveUnavailableUntilIndefinitely() {
		String result = driver.findElement(By.id("avail-status")).getText();
		assertTrue(result.contains("currently unavailable indefinitely"));
	}

	@When("user should see themselve unavailable until some time in the future")
	public void userShouldSeeThemselveUnavailableUntilSomeTimeInTheFuture() {
		String result = driver.findElement(By.id("avail-status")).getText();
		assertTrue(result.contains("currently unavailable until"));
		assertTrue(result.contains("date:"));
		assertTrue(result.contains("time:"));
	}
	@When("user should see themselve available")
	public void userShouldSeeThemselveAvailable() {
		String result = driver.findElement(By.id("avail-status")).getText();
		System.out.println(result);
		assertTrue(result.contains("currently available indefinitely"));
	}

	@When("user clicks add users button")
	public void userClicksAddUsersButton() {
		user_clicks_button("btn-add-users");
		waitForSeconds(1);
	}

	@When ("user types in newUser1 in the invitee search box")
	public void userTypesInNewUser1InTheInviteeSearchBox() {
		// waitForSeconds(1);
		System.out.println("user types in newUser1 in the invitee search box");
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser1);
		waitForSeconds(1);
	}

	@When ("user types in newUser2 in the invitee search box")
	public void userTypesInNewUser2InTheInviteeSearchBox() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser2);
		waitForSeconds(1);
	}

	@When ("user types in newUser3 in the invitee search box")
	public void userTypesInNewUser3InTheInviteeSearchBox() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser3);
		waitForSeconds(1);
	}

	@Then("user should see newUser1 unavailable not clickable")
	public void userShouldSeeNewUser1UnavailableNotClickable() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("user-card"));
		assertTrue(result.getAttribute("class").contains("unavailable"));
		assertTrue(result.getText().contains(newUser1.toLowerCase()));
	}

	@Then("user should see newUser2 unavailable not clickable")
	public void userShouldSeeNewUser2UnavailableNotClickable() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("user-card"));
		assertTrue(result.getAttribute("class").contains("unavailable"));
		assertTrue(result.getText().contains(newUser2.toLowerCase()));
	}

	@Then("user should see newUser3 unavailable not clickable")
	public void userShouldSeeNewUser3UnavailableNotClickable() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("user-card"));
		assertTrue(result.getAttribute("class").contains("unavailable"));
		assertTrue(result.getText().contains(newUser3.toLowerCase()));
	}

	/* Set Block tests */
	@When("user types in newUser1 in the block search box")
	public void userTypesInNewUser1InTheBlockSearchBox() {
		// waitForSeconds(1);
		WebElement queryBox = driver.findElement(By.id("block-users-field"));
		queryBox.click();
		queryBox.sendKeys(newUser1);
		waitForSeconds(1);
	}

	@When("user types in newUser2 in the block search box")
	public void userTypesInNewUser2InTheBlockSearchBox() {
		WebElement queryBox = driver.findElement(By.id("block-users-field"));
		queryBox.click();
		queryBox.sendKeys(newUser2);
		waitForSeconds(1);
	}

	@When("user types in newUser3 in the block search box")
	public void userTypesInNewUser3InTheBlockSearchBox() {
		WebElement queryBox = driver.findElement(By.id("block-users-field"));
		queryBox.click();
		queryBox.sendKeys(newUser3);
		waitForSeconds(1);
	}

	@When("user clicks block button")
	public void userClicksBlockButton() {
		user_clicks_button("block-but");
		waitForSeconds(1);
	}

	@When("user should see newUser1 on the block list")
	public void userShouldSeeNewUser1OnTheBlockList() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("strong-result"));
		assertTrue(result.getText().contains(newUser1.toLowerCase()));
	}

	@When("user should see newUser2 on the block list")
	public void userShouldSeeNewUser2OnTheBlockList() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("strong-result"));
		assertTrue(result.getText().contains(newUser2.toLowerCase()));
	}

	@When("user should see newUser3 on the block list")
	public void userShouldSeeNewUser3OnTheBlockList() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("strong-result"));
		assertTrue(result.getText().contains(newUser3.toLowerCase()));
	}

	@When("user clicks newUser1 in the block list")
	public void userCliksNewUser1InTheBlockList() {
		System.out.println("It tries to click");
		WebElement result = driver.findElement(By.className("strong-result"));
		result.click();
		System.out.println("It clicked");
		waitForSeconds(1);
	}

	@When("user clicks unblock button")
	public void userClicksUnblockButton() {
		user_clicks_button("unblock-but");
		waitForSeconds(1);
	}

	@Then("user should see newUser1 available and clickable")
	public void userShouldSeeNewUser1AvailableAndClickable() {
		waitForSeconds(1);
		WebElement result = driver.findElement(By.className("user-card"));
		System.out.println(result.getText());
		assertEquals(newUser1.toLowerCase(),result.getText());
	}

	/* view sent proposals tests */
	@When("user navigates to Create Proposal page")
	public void userNavigatesToCreateProposalPage() {
		user_clicks_button("create-proposal-btn");
		waitForSeconds(1);
	}

	@When("user adds newUser1 to the proposal")
	public void userAddsNewUser1ToTheProposal() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser1);
		waitForSeconds(1);
		driver.findElement(By.id("user-card-1")).click();
	}

	@When("user adds newUser2 to the proposal")
	public void userAddsNewUser2ToTheProposal() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser2);
		waitForSeconds(1);
		driver.findElement(By.id("user-card-1")).click();
	}

	@When("user adds newUser3 to the proposal")
	public void userAddsNewUser3ToTheProposal() {
		WebElement queryBox = driver.findElement(By.id("user-search-input"));
		queryBox.click();
		queryBox.sendKeys(newUser3);
		waitForSeconds(1);
		driver.findElement(By.id("user-card-1")).click();
	}

	@Then("user sees the proposal from first user on calendar")
	public void userSeesTheProposalFromFirstUserOnCalendar() {
		waitForSeconds(1);
		// TODO: check calendar
		// WebElement result = driver.findElement(By.className("proposal-card"));
		// assertTrue(result.getText().contains(newUser1.toLowerCase()));
	}

	@Then("user sees their first proposal that they sent out on the calendar")
	public void userSeesTheirFirstProposalThatTheySentOutOnTheCalendar() {
		waitForSeconds(1);
		// TODO: check calendar
	}

	@After()
	public void after() {
		driver.quit();
	}
}

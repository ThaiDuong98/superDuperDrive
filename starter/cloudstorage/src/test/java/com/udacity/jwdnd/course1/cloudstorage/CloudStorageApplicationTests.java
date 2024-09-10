package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	public final String NOTE_TAG_ID = "nav-notes-tab";
	public final String CREDENTIAL_TAG_ID = "nav-credentials-tab";
	public final String NOTE_TITLE = "NOTE_TITLE";
	public final String NOTE_DESCRIPTION = "NOTE_DESCRIPTION";
	public final String FIRST_NAME = "FIRST_NAME";
	public final String LAST_NAME = "LAST_NAME";
	public final String USERNAME = "USERNAME";
	public final String PASSWORD = "PASSWORD";
	public final String URL_FIELD = "http://w3school.com";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void waitTime(String idTag){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idTag)));
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
//		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 Testing base on the requirement
 	*/

	public void handleSwitchTag(String tagId){
		waitTime(tagId);
		WebElement tab = driver.findElement(By.id(tagId));
		tab.click();
	}

	public void redirectToHomepage(){
		// Click a tag in result page
		waitTime("home-page-link");
		WebElement homePageLink = driver.findElement(By.id("home-page-link"));
		homePageLink.click();
	}

	/**
	 * Write a Selenium test that verifies that the home page is not accessible without logging in.
	 * */

	@Test
	public void testAccessHomWithoutLogin(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	public boolean verifyListData(String tableTag, String field, String valueCompare){
		// Verify note list
		// Find the table containing notes
		waitTime(tableTag);
		WebElement notesTable = driver.findElement(By.id(tableTag));
		// Find all elements that have the class "note-title"
		List<WebElement> noteTitleElements = notesTable.findElements(By.className(field));

		boolean isSuccess = false;

		// Loop through the note title elements and check if one matches the expected note title
		for (WebElement noteTitleElement : noteTitleElements) {
			if (noteTitleElement.getText().equals(valueCompare)) {
				isSuccess = true;
				break;
			}
		}

		return isSuccess;
	}

	boolean verifyDeletedData(String tagTable, String tagField){
		waitTime(tagTable);
		WebElement notesTable = driver.findElement(By.id(tagTable));
		// Find all elements that have the class "note-title"
		List<WebElement> fieldElements = notesTable.findElements(By.className(tagField));

		boolean isNoteDeleted = true;
		for (WebElement element : fieldElements) {
			if (element.getText().equals("Hello world.")) {
				isNoteDeleted = false;
				break;
			}
		}

		return isNoteDeleted;
	}

	/**
	 Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
	 then logs out and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testUserAccessFlow(){
		doMockSignUp("Test","Test","RT","123");
		doLogIn("RT", "123");

		driver.get("http://localhost:" + this.port + "/home");

		WebElement logoutButton = driver.findElement(By.id("logout-btn"));
		logoutButton.click();

		Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/login");
		Assertions.assertNotEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home");
	}

	// Testing for note
	public void saveAndVerifyNote(){
		handleSwitchTag(NOTE_TAG_ID);

		// Click add note button
		waitTime("add-note-btn");
		WebElement addNoteButton = driver.findElement(By.id("add-note-btn"));
		addNoteButton.click();

		// Wait for the modal to be visible
		waitTime("noteModal");

		// Fill in the note form
		waitTime("note-title");
		WebElement noteTitleField = driver.findElement(By.id("note-title"));

		waitTime("note-description");
		WebElement noteDescriptionField = driver.findElement(By.id("note-description"));

		noteTitleField.sendKeys(NOTE_TITLE);
		noteDescriptionField.sendKeys(NOTE_DESCRIPTION);
		// Click save button
		WebElement saveButton = driver.findElement(By.id("save-note-btn"));
		saveButton.click();

		// Redirect to result page
		Assertions.assertEquals("Result", driver.getTitle());

		// Click a tag in result page
		redirectToHomepage();

		// Waiting back to home page
		waitTime(NOTE_TAG_ID);

		handleSwitchTag(NOTE_TAG_ID);
		boolean isVerifyNote = verifyListData("noteTable", "note-title", NOTE_TITLE);
		Assertions.assertTrue(isVerifyNote);
	}

	/**
	 * Write a Selenium test that logs in an existing user,
	 * creates a note and verifies that the note details are visible in the note list.
	 * */
	@Test
	void testAddNote()  {
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyNote();
	}

	/**
	 * Write a Selenium test that logs in an existing user with existing notes,
	 * clicks the edit note button on an existing note, changes the note data,
	 * saves the changes, and verifies that the changes appear in the note list.
	 * */
	@Test
	void testUpdateNote()  {
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyNote();

		waitTime("edit-note-btn");
		WebElement editNoteButton = driver.findElement(By.id("edit-note-btn"));
		editNoteButton.click();

		waitTime("noteModal");

		WebElement noteDescriptionField = driver.findElement(By.id("note-description"));
		WebElement noteTitleField = driver.findElement(By.id("note-title"));


		noteTitleField.clear();
		noteTitleField.sendKeys(NOTE_TITLE);

		noteDescriptionField.clear();
		noteDescriptionField.sendKeys(NOTE_DESCRIPTION);

		WebElement saveNoteButton = driver.findElement(By.id("save-note-btn"));
		saveNoteButton.click();

		redirectToHomepage();

		handleSwitchTag(NOTE_TAG_ID);

		boolean isVerifyNote = verifyListData("noteTable", "note-title", NOTE_TITLE);
		Assertions.assertTrue(isVerifyNote);
	}

	/**
	 * Write a Selenium test that logs in an existing user with existing notes,
	 * clicks the delete note button on an existing note,
	 * and verifies that the note no longer appears in the note list.
	 * */
	@Test
	void testDeleteNote(){
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyNote();

		waitTime("delete-note-btn");
		WebElement deleteNoteButton = driver.findElement(By.id("delete-note-btn"));
		deleteNoteButton.click();

		// Click a tag in result page
		redirectToHomepage();
		handleSwitchTag(NOTE_TAG_ID);

		boolean isDeleteSuccess = verifyDeletedData("noteTable", "note-title");
		// Assert that the note is no longer in the list
		Assertions.assertTrue(isDeleteSuccess);
	}

	/**
	 * Testing for credential
	 * */

	public void saveAndVerifyCredential(){
		handleSwitchTag(CREDENTIAL_TAG_ID);

		// Click add note button
		waitTime("add-credential-btn");
		WebElement addNoteButton = driver.findElement(By.id("add-credential-btn"));
		addNoteButton.click();

		// Wait for the modal to be visible
		waitTime("credentialModal");

		// Fill in the note form
		waitTime("credential-username");
		WebElement urlField = driver.findElement(By.id("credential-url"));

		waitTime("credential-username");
		WebElement userNameField = driver.findElement(By.id("credential-username"));

		waitTime("credential-password");
		WebElement passwordField = driver.findElement(By.id("credential-password"));

		urlField.sendKeys(URL_FIELD);
		userNameField.sendKeys(USERNAME);
		passwordField.sendKeys(PASSWORD);

		// Click save button
		WebElement saveButton = driver.findElement(By.id("save-credential-btn"));
		saveButton.click();

		// Redirect to result page
		Assertions.assertEquals("Result", driver.getTitle());

		// Click a tag in result page
		redirectToHomepage();

		// Waiting back to home page
		waitTime(CREDENTIAL_TAG_ID);

		handleSwitchTag(CREDENTIAL_TAG_ID);

		boolean isVerifyCredential = verifyListData("credentialTable", "credential-url", URL_FIELD);
		Assertions.assertTrue(isVerifyCredential);
	}

	/**
	 * Write a Selenium test that logs in an existing user,
	 * creates a credential and
	 * verifies that the credential details are visible in the credential list.
	 * */
	@Test
	void testAddAndVerifyCredential(){
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyCredential();
	}

	/**
	 * Write a Selenium test that logs in an existing user with existing credentials,
	 * clicks the edit credential button on an existing credential,
	 * changes the credential data, saves the changes,
	 * and verifies that the changes appear in the credential list.
	 * */
	@Test
	void testUpdateCredential(){
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyCredential();

		waitTime("edit-credential-btn");
		WebElement editCredentialButton = driver.findElement(By.id("edit-credential-btn"));
		editCredentialButton.click();

		waitTime("credentialModal");

		WebElement urlField = driver.findElement(By.id("credential-url"));
		WebElement userNameField = driver.findElement(By.id("credential-username"));
		WebElement passwordField = driver.findElement(By.id("credential-password"));


		urlField.clear();
		urlField.sendKeys(URL_FIELD);

		userNameField.clear();
		userNameField.sendKeys(USERNAME);

		passwordField.clear();
		passwordField.sendKeys(PASSWORD);

		WebElement saveButton = driver.findElement(By.id("save-credential-btn"));
		saveButton.click();

		redirectToHomepage();

		handleSwitchTag(CREDENTIAL_TAG_ID);

		boolean isVerifyCredential = verifyListData("credentialTable", "credential-url", URL_FIELD);
		Assertions.assertTrue(isVerifyCredential);
	}

	/**
	 * Write a Selenium test that logs in an existing user with existing credentials,
	 * clicks the delete credential button on an existing credential,
	 * and verifies that the credential no longer appears in the credential list.
	 * */
	@Test
	void testDeleteCredential(){
		doMockSignUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
		doLogIn(USERNAME, PASSWORD);
		saveAndVerifyCredential();

		waitTime("delete-credential-btn");
		WebElement deleteNoteButton = driver.findElement(By.id("delete-credential-btn"));
		deleteNoteButton.click();

		// Click a tag in result page
		redirectToHomepage();
		handleSwitchTag(CREDENTIAL_TAG_ID);

		boolean isDeleteSuccess = verifyDeletedData("credentialTable", "credential-url");
		Assertions.assertTrue(isDeleteSuccess);
	}
}

package com.luma.shop.navigation.pages.account;

import com.luma.shop.navigation.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateAccountPage extends BasePage {
  private final By firstNameField = By.id("firstname");
  private final By lastNameField = By.id("lastname");
  private final By emailField = By.id("email_address");
  private final By passwordField = By.id("password");
  private final By confirmPasswordField = By.id("password-confirmation");
  private final By createAccountButton = By.cssSelector(".action.submit.primary");
  private final By successMessage = By.cssSelector("[data-ui-id='message-success']");
  private final By emailLoginField = By.id("email");
  private final By passwordLoginField = By.id("pass");
  private final By logoutButton =
      By.cssSelector("/html/body/div[2]/header/div[1]/div/ul/li[2]/div/ul/li[3]/a");
  private final By loginButton = By.id("send2");
  private final By accountInfoBlock = By.cssSelector(".block.block-dashboard-info");

  public CreateAccountPage(WebDriver driver) {
    super(driver);
  }

  public void navigateToCreateAccountPage() {
    driver.get("https://magento.softwaretestingboard.com/customer/account/create/");
    waitForElementVisible(createAccountButton);
  }

  public void logout() {
    WebElement lgbtn = driver.findElement(logoutButton);
    lgbtn.click();
  }

  public void navigateToLoginPage() {
    driver.get("https://magento.softwaretestingboard.com/customer/account/login/");
    waitForElementVisible(loginButton);
  }

  public void fillLoginForm(String email, String password) {
    typeText(emailLoginField, email);
    typeText(passwordLoginField, password);
  }

  public void clickLoginAccountButton() {
    clickElement(loginButton);
  }

  public boolean isLoginSuccessful() {
    try {
      return waitForElementVisible(accountInfoBlock).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public void loginNewAccount(String email, String password) {
    fillLoginForm(email, password);
    clickLoginAccountButton();
  }

  public void fillRegistrationForm(
      String firstName, String lastName, String email, String password) {
    typeText(firstNameField, firstName);
    typeText(lastNameField, lastName);
    typeText(emailField, email);
    typeText(passwordField, password);
    typeText(confirmPasswordField, password);
  }

  public void clickCreateAccountButton() {
    clickElement(createAccountButton);
  }

  public boolean isRegistrationSuccessful() {
    try {
      return waitForElementVisible(successMessage).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public String getSuccessMessage() {
    try {
      return getText(successMessage);
    } catch (Exception e) {
      return "";
    }
  }

  public void registerNewAccount(String firstName, String lastName, String email, String password) {
    fillRegistrationForm(firstName, lastName, email, password);
    clickCreateAccountButton();
  }
}

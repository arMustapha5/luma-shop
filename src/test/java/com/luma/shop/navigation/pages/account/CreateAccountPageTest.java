package com.luma.shop.navigation.pages.account;

import static org.junit.jupiter.api.Assertions.*;

import com.luma.shop.navigation.pages.base.BasePageTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateAccountPageTest extends BasePageTest {

  private static String uniqueEmail;

  @BeforeAll
  public static void setup() {
    uniqueEmail = "test" + System.currentTimeMillis() + "@example.com";
  }

  @Test
  @DisplayName("User should be able to create a new account")
  public void testCreateAccount() {
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    createAccountPage.navigateToCreateAccountPage();

    createAccountPage.registerNewAccount("John", "Doe", uniqueEmail, "Password123!");

    assertTrue(
        createAccountPage.isRegistrationSuccessful(),
        "Registration should be successful with valid information");

    String successMessage = createAccountPage.getSuccessMessage();
    assertTrue(
        successMessage.contains("Thank you for registering")
            || successMessage.contains("account has been created"),
        "Success message should indicate successful registration");
  }

  @Test
  @DisplayName("User should be able to log in after account creation")
  public void testLoginAfterAccountCreation() {
    CreateAccountPage createAccountPage = new CreateAccountPage(driver);
    createAccountPage.navigateToLoginPage();

    createAccountPage.loginNewAccount(uniqueEmail, "Password123!");

    assertTrue(
        createAccountPage.isLoginSuccessful(),
        "Login should be successful with the created account");
  }
}

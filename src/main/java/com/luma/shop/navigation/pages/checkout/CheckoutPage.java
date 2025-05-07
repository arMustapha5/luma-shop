package com.luma.shop.navigation.pages.checkout;

import com.luma.shop.navigation.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {
  private final By emailField = By.id("customer-email");
  private final By firstNameField = By.name("firstname");
  private final By lastNameField = By.name("lastname");
  private final By streetAddressField = By.name("street[0]");
  private final By cityField = By.name("city");
  private final By regionField = By.name("region_id");
  private final By zipCodeField = By.name("postcode");
  private final By countryField = By.name("country_id");
  private final By phoneNumberField = By.name("telephone");
  private final By shippingMethodRadio = By.cssSelector("input[type='radio'][name='ko_unique_1']");
  private final By nextButton = By.cssSelector(".button.action.continue.primary");

  private final By orderSummary = By.className("opc-block-summary");
  //  private final By placeOrderButton = By.cssSelector(".action.primary.checkout");

  private final By placeOrderButton =
      By.xpath(
          "/html/body/div[3]/main/div[2]/div/div[2]/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[2]/div[2]/div[4]/div/button");

  private final By billingSameAsShippingCheckbox =
      By.id("billing-address-same-as-shipping-checkmo");
  private final By orderConfirmation = By.cssSelector(".page-title");
  private final By orderNumber = By.cssSelector(".checkout-success p span");

  public CheckoutPage(WebDriver driver) {
    super(driver);
  }

  public void fillShippingInformation(
      String email,
      String firstName,
      String lastName,
      String street,
      String city,
      String state,
      String zipCode,
      String country,
      String phone) {
    typeText(emailField, email);
    typeText(firstNameField, firstName);
    typeText(lastNameField, lastName);
    typeText(streetAddressField, street);
    typeText(cityField, city);

    try {
      Select regionSelect = new Select(driver.findElement(regionField));
      regionSelect.selectByVisibleText(state);
    } catch (Exception e) {
      System.out.println("Could not select region: " + e.getMessage());
    }

    typeText(zipCodeField, zipCode);

    try {
      Select countrySelect = new Select(driver.findElement(countryField));
      countrySelect.selectByVisibleText(country);
    } catch (Exception e) {
      System.out.println("Could not select country: " + e.getMessage());
    }

    typeText(phoneNumberField, phone);
  }

  public void selectFirstShippingMethod() {
    try {
      waitForElementClickable(shippingMethodRadio).click();
    } catch (Exception e) {
      System.out.println("Could not select shipping method: " + e.getMessage());
    }
  }

  public void clickNextButton() {
    clickElement(nextButton);
  }

  public void waitForOrderConfirmation() {
    try {
      waitForElementVisible(orderConfirmation);
    } catch (Exception e) {
      System.out.println("Order confirmation page not visible: " + e.getMessage());
    }
  }

  public void placeOrder() {
    try {
      waitForElementClickable(placeOrderButton);
      clickElement(placeOrderButton);
    } catch (Exception e) {
      System.out.println("Could not place order: " + e.getMessage());
    }
  }

  public boolean isOrderConfirmed() {
    try {
      return waitForElementVisible(orderConfirmation).isDisplayed()
          && getText(orderConfirmation).contains("Thank you for your purchase!");
    } catch (Exception e) {
      return false;
    }
  }

  public void checkBillingSameAsShipping() {
    try {
      WebElement checkbox = waitForElementVisible(billingSameAsShippingCheckbox);
      if (!checkbox.isSelected()) {
        checkbox.click();
      }
    } catch (Exception e) {
      System.out.println("Could not check 'billing same as shipping': " + e.getMessage());
    }
  }

  public String getOrderNumber() {
    try {
      return getText(orderNumber);
    } catch (Exception e) {
      return "";
    }
  }

  public void completeCheckout(
      String email,
      String firstName,
      String lastName,
      String street,
      String city,
      String state,
      String zipCode,
      String country,
      String phone) {
    fillShippingInformation(
        email, firstName, lastName, street, city, state, zipCode, country, phone);
    selectFirstShippingMethod();
    clickNextButton();

    waitForElementVisible(orderSummary);
    checkBillingSameAsShipping();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    placeOrder();
    waitForOrderConfirmation();
  }
}

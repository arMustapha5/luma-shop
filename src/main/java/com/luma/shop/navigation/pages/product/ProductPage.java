package com.luma.shop.navigation.pages.product;

import com.luma.shop.navigation.pages.base.BasePage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {
  private final By productName = By.className("page-title");
  private final By productPrice = By.className("price");
  private final By addToCartButton = By.id("product-addtocart-button");
  private final By sizeOptions = By.cssSelector(".swatch-option.text");
  private final By colorOptions = By.cssSelector(".swatch-option.color");
  private final By quantityInput = By.id("qty");
  private final By successMessage = By.cssSelector("[data-ui-id='message-success']");
  private final By cartCounter = By.className("counter-number");

  public ProductPage(WebDriver driver) {
    super(driver);
  }

  public String getProductName() {
    return getText(productName);
  }

  public String getProductPrice() {
    return getText(productPrice);
  }

  public void selectSize(int index) {
    List<WebElement> sizes = driver.findElements(sizeOptions);
    if (index >= 0 && index < sizes.size()) {
      sizes.get(index).click();
    } else {
      throw new IndexOutOfBoundsException("Size index out of bounds");
    }
  }

  public void selectColor(int index) {
    List<WebElement> colors = driver.findElements(colorOptions);
    if (index >= 0 && index < colors.size()) {
      colors.get(index).click();
    } else {
      throw new IndexOutOfBoundsException("Color index out of bounds");
    }
  }

  public void setQuantity(int quantity) {
    WebElement qtyInput = waitForElementVisible(quantityInput);
    qtyInput.clear();
    qtyInput.sendKeys(String.valueOf(quantity));
  }

  public void clickAddToCart() {
    clickElement(addToCartButton);
  }

  public boolean isSuccessMessageDisplayed() {
    try {
      return waitForElementVisible(successMessage).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public String getCartCount() {
    try {
      return waitForElementVisible(cartCounter).getText();
    } catch (Exception e) {
      return "0";
    }
  }

  public void addProductToCart() {
    try {
      selectSize(0);
      selectColor(1);
      setQuantity(1);
      clickAddToCart();
      waitForElementVisible(successMessage);
    } catch (Exception e) {
      clickAddToCart();
      waitForElementVisible(successMessage);
    }
  }
}

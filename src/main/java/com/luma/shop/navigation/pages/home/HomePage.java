package com.luma.shop.navigation.pages.home;

import com.luma.shop.navigation.pages.base.BasePage;
import com.luma.shop.navigation.pages.cart.CartPage;
import com.luma.shop.navigation.pages.product.ProductPage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
  private final By productItems = By.className("product-item");
  private final By productNames = By.className("product-item-link");
  private final By headerLogo = By.className("logo");
  private final By cartIcon = By.className("showcart");
  private final By accountIcon = By.className("authorization-link");
  private final By createAccountLink = By.linkText("Create an Account");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  public void navigateToHomePage() {
    driver.get("https://magento.softwaretestingboard.com/");
    waitForElementVisible(headerLogo);
  }

  public int getNumberOfProductsDisplayed() {
    List<WebElement> products = driver.findElements(productItems);
    return products.size();
  }

  public void clickOnProduct(int index) {
    List<WebElement> products = driver.findElements(productNames);
    if (index >= 0 && index < products.size()) {
      products.get(index).click();
    } else {
      throw new IndexOutOfBoundsException("Product index out of bounds");
    }
  }

  public ProductPage clickOnFirstProduct() {
    clickOnProduct(0);
    return new ProductPage(driver);
  }

  public CartPage navigateToCart() {
    clickElement(cartIcon);
    return new CartPage(driver);
  }

  public void navigateToCreateAccount() {
    clickElement(accountIcon);
    clickElement(createAccountLink);
  }

  public void hoverOverProduct(int index) {
    List<WebElement> products = driver.findElements(productItems);
    if (index >= 0 && index < products.size()) {
      hoverOverElement(By.xpath("(//div[@class='product-item-info'])[" + (index + 1) + "]"));
    } else {
      throw new IndexOutOfBoundsException("Product index out of bounds");
    }
  }

  public boolean isAddToCartButtonVisible(int index) {
    try {
      return driver
          .findElement(
              By.xpath(
                  "(//div[@class='product-item-info'])["
                      + (index + 1)
                      + "]//button[@title='Add to Cart']"))
          .isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isAddToWishlistButtonVisible(int index) {
    try {
      return driver
          .findElement(
              By.xpath(
                  "(//div[@class='product-item-info'])["
                      + (index + 1)
                      + "]//a[@title='Add to Wish List']"))
          .isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isAddToCompareButtonVisible(int index) {
    try {
      return driver
          .findElement(
              By.xpath(
                  "(//div[@class='product-item-info'])["
                      + (index + 1)
                      + "]//a[@title='Add to Compare']"))
          .isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}

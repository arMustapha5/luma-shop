package com.luma.shop.navigation.pages.cart;

import com.luma.shop.navigation.pages.base.BasePage;
import com.luma.shop.navigation.pages.checkout.CheckoutPage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
  private final By cartPageTotal = By.cssSelector(".counter-number");
  private final By cartItems = By.cssSelector(".cart.item");
  private final By cartItemName = By.cssSelector(".product-item-name a");
  private final By cartItemPrice = By.cssSelector(".price .price");
  private final By cartTotal = By.cssSelector(".grand.totals .price");
  private final By removeItemButton = By.cssSelector(".action-delete");
  private final By proceedToCheckoutButton =
      By.cssSelector(".checkout-methods-items .action.primary.checkout");
  private final By emptyCartMessage = By.cssSelector(".cart-empty");

  public CartPage(WebDriver driver) {
    super(driver);
  }

  public void navigateToCart() {
    driver.get("https://magento.softwaretestingboard.com/checkout/cart/");
    // Wait for page to load
    wait.until(driver -> driver.findElement(By.cssSelector(".page-title")) != null);
  }

  public int getNumberOfItemsInCartCount() {
    try {
      String countText = driver.findElement(cartPageTotal).getText();
      return Integer.parseInt(countText.trim());
    } catch (Exception e) {
      return 0;
    }
  }

  public int getNumberOfItemsInCart() {
    try {
      return driver.findElements(cartItems).size();
    } catch (Exception e) {
      return 0;
    }
  }

  public List<WebElement> getCartItems() {
    return driver.findElements(cartItems);
  }

  public String getItemName(int index) {
    List<WebElement> items = driver.findElements(cartItemName);
    if (index >= 0 && index < items.size()) {
      return items.get(index).getText();
    }
    return null;
  }

  public String getItemPrice(int index) {
    List<WebElement> prices = driver.findElements(cartItemPrice);
    if (index >= 0 && index < prices.size()) {
      return prices.get(index).getText();
    }
    return null;
  }

  public String getCartTotal() {
    try {
      return getText(cartTotal);
    } catch (Exception e) {
      return "$0.00";
    }
  }

  public void removeItemFromCart(int index) {
    List<WebElement> removeButtons = driver.findElements(removeItemButton);
    if (index >= 0 && index < removeButtons.size()) {
      removeButtons.get(index).click();
    }
  }

  public void removeFirstItem() {
    removeItemFromCart(0);
  }

  public CheckoutPage proceedToCheckout() {
    clickElement(proceedToCheckoutButton);
    return new CheckoutPage(driver);
  }

  public boolean isCartEmpty() {
    try {
      return driver.findElement(emptyCartMessage).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isCheckoutButtonEnabled() {
    try {
      return driver.findElement(proceedToCheckoutButton).isEnabled();
    } catch (Exception e) {
      return false;
    }
  }
}

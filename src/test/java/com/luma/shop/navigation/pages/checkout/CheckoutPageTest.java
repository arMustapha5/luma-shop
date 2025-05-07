package com.luma.shop.navigation.pages.checkout;

import static org.junit.jupiter.api.Assertions.*;

import com.luma.shop.navigation.pages.base.BasePageTest;
import com.luma.shop.navigation.pages.cart.CartPage;
import com.luma.shop.navigation.pages.home.HomePage;
import com.luma.shop.navigation.pages.product.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutPageTest extends BasePageTest {

  @Test
  @DisplayName("User should be able to complete checkout process")
  public void testCompletingCheckoutProcess() {

    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    ProductPage productPage = homePage.clickOnFirstProduct();
    productPage.addProductToCart();

    CartPage cartPage = new CartPage(driver);
    cartPage.navigateToCart();

    CheckoutPage checkoutPage = cartPage.proceedToCheckout();

    checkoutPage.completeCheckout(
        "test" + System.currentTimeMillis() + "@example.com",
        "John",
        "Doe",
        "123 Main St",
        "New York",
        "New York",
        "10001",
        "United States",
        "1234567890");

    assertFalse(
        checkoutPage.getOrderNumber().isEmpty(),
        "Order number should be generated after successful checkout");
  }
}

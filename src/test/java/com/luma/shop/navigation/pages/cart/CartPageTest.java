package com.luma.shop.navigation.pages.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.luma.shop.navigation.pages.base.BasePageTest;
import com.luma.shop.navigation.pages.home.HomePage;
import com.luma.shop.navigation.pages.product.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartPageTest extends BasePageTest {

  @Test
  @DisplayName("User should be able to add a product to cart")
  public void testAddProductToCart() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    ProductPage productPage = homePage.clickOnFirstProduct();
    String productName = productPage.getProductName();

    productPage.addProductToCart();

    assertTrue(
        productPage.isSuccessMessageDisplayed(),
        "Success message should be displayed after adding item to cart");

    CartPage cartPage = new CartPage(driver);
    cartPage.navigateToCart();

    assertEquals(
        1, cartPage.getNumberOfItemsInCart(), "Cart should contain 1 item after adding a product");
  }

  @Test
  @DisplayName("User should be able to remove product from cart")
  public void testRemoveProductFromCart() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    ProductPage productPage = homePage.clickOnFirstProduct();
    productPage.addProductToCart();

    CartPage cartPage = new CartPage(driver);
    cartPage.navigateToCart();

    assertEquals(1, cartPage.getNumberOfItemsInCart(), "Cart should contain 1 item before removal");

    cartPage.removeFirstItem();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing the only item");
  }

  @Test
  @DisplayName("Cart should update total when adding products")
  public void testCartTotalUpdatesWhenAddingProducts() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    ProductPage productPage = homePage.clickOnFirstProduct();
    productPage.addProductToCart();

    homePage.navigateToHomePage();

    productPage = homePage.clickOnFirstProduct();
    productPage.addProductToCart();

    CartPage cartPage = new CartPage(driver);
    cartPage.navigateToCart();

    assertEquals(
        2,
        cartPage.getNumberOfItemsInCartCount(),
        "Cart should contain 2 items after adding two products");

    assertNotEquals(
        "$0.00", cartPage.getCartTotal(), "Cart total should not be $0.00 after adding products");
  }

  @Test
  @DisplayName("Checkout button should be enabled when cart has items")
  public void testCheckoutButtonEnabledWithItems() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    ProductPage productPage = homePage.clickOnFirstProduct();
    productPage.addProductToCart();

    CartPage cartPage = new CartPage(driver);
    cartPage.navigateToCart();

    assertTrue(
        cartPage.isCheckoutButtonEnabled(),
        "Checkout button should be enabled when cart has items");
  }
}

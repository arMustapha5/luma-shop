package com.luma.shop.navigation.pages.home;

import static org.junit.jupiter.api.Assertions.*;

import com.luma.shop.navigation.pages.base.BasePageTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HomePageTest extends BasePageTest {

  @Test
  @DisplayName("Homepage should display products")
  public void testHomepageDisplaysProducts() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    int numberOfProducts = homePage.getNumberOfProductsDisplayed();
    assertTrue(numberOfProducts > 0, "Homepage should display at least one product");
  }

  @Test
  @DisplayName("User should be able to navigate to a product page")
  public void testNavigationToProductPage() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    homePage.clickOnFirstProduct();

    assertTrue(
        driver.getCurrentUrl().contains(".html"),
        "After clicking a product, URL should change to a product page");
  }

  @Test
  @DisplayName("Hovering over product should show action buttons")
  public void testProductHoverShowsActionButtons() {
    HomePage homePage = new HomePage(driver);
    homePage.navigateToHomePage();

    homePage.hoverOverProduct(0);

    assertTrue(
        homePage.isAddToCartButtonVisible(0), "Add to Cart button should be visible on hover");
    assertTrue(
        homePage.isAddToWishlistButtonVisible(0),
        "Add to Wishlist button should be visible on hover");
    assertTrue(
        homePage.isAddToCompareButtonVisible(0),
        "Add to Compare button should be visible on hover");
  }
}

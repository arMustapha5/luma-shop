package com.luma.shop.navigation.pages.base;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected Actions actions;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    this.actions = new Actions(driver);
    PageFactory.initElements(driver, this);
  }

  protected WebElement waitForElementVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected WebElement waitForElementClickable(By locator) {
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void clickElement(By locator) {
    waitForElementClickable(locator).click();
  }

  protected void typeText(By locator, String text) {
    WebElement element = waitForElementVisible(locator);
    element.clear();
    element.sendKeys(text);
  }

  protected String getText(By locator) {
    return waitForElementVisible(locator).getText();
  }

  protected void hoverOverElement(By locator) {
    WebElement element = waitForElementVisible(locator);
    actions.moveToElement(element).perform();
  }

  protected void waitForLoaderToDisappear() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-mask")));
  }
}

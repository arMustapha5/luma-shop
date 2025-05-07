package com.luma.shop.navigation.pages.base;

import java.time.Duration;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BasePageTest {
  protected WebDriver driver;
  protected final String BASE_URL = "https://magento.softwaretestingboard.com/";
  private static final Logger LOGGER = Logger.getLogger(BasePageTest.class.getName());

  @BeforeEach
  public void setUp() {
    LOGGER.info("Setting up Chrome WebDriver");
    ChromeOptions options = new ChromeOptions();

    boolean isCI = System.getenv("CI") != null;

    if (isCI) {
      options.addArguments("--headless");
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
    }

    options.addArguments("--headless");
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--start-maximized");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-gpu");
    options.addArguments("--disable-popup-blocking");

    driver = new ChromeDriver(options);

    // Common config
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    driver.manage().window().maximize();

    LOGGER.info("Chrome WebDriver setup complete.");
  }

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      LOGGER.info("Closing Chrome WebDriver");
      driver.quit();
    }
  }
}

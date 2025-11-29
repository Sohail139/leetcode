package demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("[INFO] Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("[WARN] Could not maximize window. " + e.getMessage());
        }
        // Use Duration API for implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void endTest() {
        System.out.println("[INFO] End Test: Closing browser");
        if (driver != null) {
            driver.quit();
        }
    }

    public void testCase01() throws InterruptedException {
        System.out.println("[INFO] ==== Start Test case: testCase01 ====");
        driver.get("https://leetcode.com/");
        Thread.sleep(2000);

        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains("leetcode")) {
            System.out.println("[PASS] URL contains 'leetcode'");
        } else {
            System.out.println("[FAIL] URL does not contain 'leetcode'. Found: " + currentURL);
        }
        System.out.println("[INFO] ==== End Test case: testCase01 ====");
    }

    public void testCase02() throws InterruptedException {
        System.out.println("[INFO] ==== Start Test case: testCase02 ====");
        // Navigate directly to problemset for determinism
        driver.get("https://leetcode.com/problemset/all/");
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("problemset")) {
            System.out.println("[PASS] Current URL contains 'problemset' -> " + currentUrl);
        } else {
            System.out.println("[FAIL] Current URL does not contain 'problemset'. Found: " + currentUrl);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(@href,'/problems/') and normalize-space(string())!='']")));
        } catch (Exception e) {
            System.out.println("[WARN] Problem links did not appear quickly: " + e.getMessage());
        }

        List<WebElement> problemLinks = driver.findElements(By.xpath(
                "//a[contains(@href,'/problems/') and normalize-space(string())!='']"));

        if (problemLinks.size() >= 5) {
            System.out.println("[PASS] Found at least 5 problems (found: " + problemLinks.size() + ")");
        } else {
            System.out.println("[WARN] Found fewer than 5 problems (found: " + problemLinks.size() + ")");
        }

        // Print first five problems but start the loop at i = 1 (use get(i-1) for indexing)
        int toPrint = Math.min(5, problemLinks.size());
        for (int i = 1; i <= toPrint; i++) {
            try {
                String title = problemLinks.get(i).getText().trim();
                System.out.println("[INFO] " + i + ". " + title);
            } catch (Exception e) {
                System.out.println("[WARN] Could not read text for problem index " + (i) + ": " + e.getMessage());
            }
        }

        // Now explicitly read the first five titles into variables (safe checks)
        String first = problemLinks.size() >= 1 ? problemLinks.get(1).getText().trim() : "";
        String second = problemLinks.size() >= 2 ? problemLinks.get(2).getText().trim() : "";
        String third = problemLinks.size() >= 3 ? problemLinks.get(3).getText().trim() : "";
        String fourth = problemLinks.size() >= 4 ? problemLinks.get(4).getText().trim() : "";
        String fifth = problemLinks.size() >= 5 ? problemLinks.get(5).getText().trim() : "";

        if ("Two Sum".equalsIgnoreCase(first)) {
            System.out.println("[PASS] First problem is 'Two Sum'");
        } else {
            System.out.println("[WARN] First problem is not 'Two Sum'. Found: " + first);
        }

        if ("Add Two Numbers".equalsIgnoreCase(second)) {
            System.out.println("[PASS] Second problem is 'Add Two Numbers'");
        } else {
            System.out.println("[WARN] Second problem expected 'Add Two Numbers'. Found: " + second);
        }

        if ("Longest Substring Without Repeating Characters".equalsIgnoreCase(third)) {
            System.out.println("[PASS] Third problem is 'Longest Substring Without Repeating Characters'");
        } else {
            System.out.println("[WARN] Third problem expected 'Longest Substring Without Repeating Characters'. Found: " + third);
        }

        if ("Median of Two Sorted Arrays".equalsIgnoreCase(fourth)) {
            System.out.println("[PASS] Fourth problem is 'Median of Two Sorted Arrays'");
        } else {
            System.out.println("[WARN] Fourth problem expected 'Median of Two Sorted Arrays'. Found: " + fourth);
        }

        if ("Longest Palindromic Substring".equalsIgnoreCase(fifth)) {
            System.out.println("[PASS] Fifth problem is 'Longest Palindromic Substring'");
        } else {
            System.out.println("[WARN] Fifth problem expected 'Longest Palindromic Substring'. Found: " + fifth);
        }

        // Find where "Two Sum" is in the list (if present) and report its 1-based position
        int twoSumPos = 1;
        for (int i = 1; i < problemLinks.size(); i++) {
            try {
                String t = problemLinks.get(i).getText().trim();
                if ("Two Sum".equalsIgnoreCase(t)) {
                    twoSumPos = i; // 1-based position for printing
                    System.out.println("[INFO] Found 'Two Sum' at position: " + twoSumPos + " (list index " + i + ")");
                    break;
                }
            } catch (Exception ignored) {}
        }
        if (twoSumPos == -1) {
            System.out.println("[WARN] 'Two Sum' was not found among the anchors collected on the page.");
        }

        System.out.println("[INFO] ==== End Test case: testCase02 ====");
    }

    public void testCase03() throws InterruptedException {
        System.out.println("[INFO] ==== Start Test case: testCase03 ====");
        driver.get("https://leetcode.com/problemset/all/");
        Thread.sleep(2000); 
        WebElement twoSumLink = driver.findElement(By.xpath("//div[text()='1. Two Sum']/ancestor::a"));
        twoSumLink.click();
        String currentURL = driver.getCurrentUrl();
        if(currentURL.contains("two-sum")){
            System.out.println("[PASS] URL contains 'two-sum'");
        }
        else{
            System.out.println("[FAIL] URL does not contain 'two-sum'. Found: " + currentURL);
        }

    }

    public void testCase04() {
        System.out.println(" Start Test case : testCase04 ");
        driver.get("https://leetcode.com/problems/two-sum/");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        WebElement el =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Submissions']/parent::div")));
        el.click();

      WebElement text=  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Register or Log in']")));
      String actualText = text.getText().trim();
      if(actualText.equals("Register or Log in")){
        System.out.println("[PASS] 'Register or Log in' is displayed");
      }
      else{
        System.out.println("[FAIL] 'Register or Log in' is not displayed. Found: " + actualText);
      }
      System.out.println("END Test case : testCase04");
}
}
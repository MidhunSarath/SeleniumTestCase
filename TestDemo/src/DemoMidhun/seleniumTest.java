package DemoMidhun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.NoSuchElementException;

public class seleniumTest {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriverwin32\\chromedriver.exe");
        
        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();
        
        // Maximize the browser window
        driver.manage().window().maximize();
        try {
        // Navigate to FitPeo Homepage
        driver.get("https://www.fitpeo.com");

        // Navigate to the Revenue Calculator Page
        driver.findElement(By.linkText("Revenue Calculator")).click();
        
        // Wait for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Scroll to the Slider
        WebElement element = driver.findElement(By.xpath("//h5[contains(text(),'Total Gross Revenue Per Year')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        
        // Locate the slider element
        WebElement slider = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-thumb')]"));

        // Locate the slider rail element
        WebElement sliderRail = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-rail')]"));

        // Get the width of the slider rail
        int railWidth = sliderRail.getSize().width;
        System.out.println(railWidth);

        // Define the initial, target, min and max values
        int initialValue = 200;
        int targetValue = 820;
        int minValue = 0;
        int maxValue = 2000;

        // Calculate the total range and the step size per pixel
        int totalRange = maxValue - minValue;
        System.out.println(totalRange);
        double stepSize = (double) totalRange / railWidth;
        //double stepSize=4;
        System.out.println(stepSize);
        // Calculate the necessary offset in pixels
        int offset = (int) ((targetValue - initialValue) / stepSize);
        
        System.out.println(offset);

        // Create an instance of Actions class
        Actions actions = new Actions(driver);

        // Move the slider to the desired position
        actions.clickAndHold(slider)
               .moveByOffset(offset,0)
               .release()
               .perform();
             
        // add a sleep to observe the slider movement
        Thread.sleep(3000);
        
        
        // Locate the input field
        WebElement inputField = driver.findElement(By.xpath("//input[@type='number' and @min='0' and @max='2000']"));
        inputField.sendKeys(Keys.CONTROL + "a");
        inputField.sendKeys(Keys.BACK_SPACE);
        inputField.sendKeys("560");

        // Validate Slider Value is updated to 560
        int sliderValue = Integer.parseInt(inputField.getAttribute("value"));
        if (sliderValue == 560) {
            System.out.println("Slider value correctly updated to 560.");
        } else {
            System.out.println("Slider value did not update correctly.");
        }
        
        // add a sleep to observe the changes
        Thread.sleep(2000);
             
        // Select CPT Codes checkboxes
        driver.findElement(By.xpath("//p[contains(text(),'CPT-99091')]//following::input[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[contains(text(),'CPT-99453')]//following::input[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[contains(text(),'CPT-99454')]//following::input[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[contains(text(),'CPT-99474')]//following::input[1]")).click();
        Thread.sleep(2000);
        
        // Set the value back to 820
        //WebElement inputField = driver.findElement(By.xpath("//input[@type='number' and @min='0' and @max='2000']"));
        inputField.sendKeys(Keys.CONTROL + "a");
        inputField.sendKeys(Keys.BACK_SPACE);
        inputField.sendKeys("820");
        
        // Validate Total Recurring Reimbursement value
        WebElement reimbursementHeader = driver.findElement(By.xpath("//p[contains(text(),'Total Recurring Reimbursement for all Patients Per Month')]//p"));
        String reimbursementText = reimbursementHeader.getText();
        System.out.println(reimbursementText);
        Thread.sleep(3000);
        if (reimbursementText.contains("$110700")) {
            System.out.println("Total Recurring Reimbursement value is correct.");
        } else {
            System.out.println("Total Recurring Reimbursement value is incorrect.");
        }
        }catch (NoSuchElementException e) {
            // If the test case failed, throw an assertion error
            throw new AssertionError("Test Failed: Criteria not satisfied", e);

        } finally {
            // Quit the driver
            driver.quit();
        }
        
}
}

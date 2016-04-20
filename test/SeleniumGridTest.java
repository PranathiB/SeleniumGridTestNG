import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

public class SeleniumGridTest {
    WebDriver driver = null;
    private StringBuffer verificationErrors = new StringBuffer();


    @Parameters({ "platform","browser","version", "url" ,"nodeURL"})
    @BeforeTest(alwaysRun=true)
    public void setup(String platform, String browser, String
            version, String url, String nodeURL) throws MalformedURLException
    {
        DesiredCapabilities caps = new DesiredCapabilities();

        if(platform.equalsIgnoreCase("MAC"))
            caps.setPlatform(org.openqa.selenium.Platform.MAC);

        if(browser.equalsIgnoreCase("Internet Explorer"))
            caps = DesiredCapabilities.internetExplorer();
        if(browser.equalsIgnoreCase("Firefox"))
            caps = DesiredCapabilities.firefox();
        if(browser.equalsIgnoreCase("Chrome"))
            caps = DesiredCapabilities.chrome();
        caps.setVersion(version);
//        driver = new RemoteWebDriver(new URL("http:localhost/wd/hub"), caps);//if grid and node are same
        driver = new RemoteWebDriver(new URL(nodeURL), caps);
        driver.get(url);
    }

    @Test(description="Test Bmi Calculator")
    public void testBmiCalculator() throws InterruptedException {
        WebElement height = driver.findElement(By.name("heightCMS"));
        height.sendKeys("181");

        WebElement weight = driver.findElement(By.name("weightKg"));
        weight.sendKeys("80");
        WebElement calculateButton = driver.findElement(By.id("Calculate"));
        calculateButton.click();

        try {
            WebElement bmi = driver.findElement(By.name("bmi"));
            assertEquals(bmi.getAttribute("value"),"24.4");

            WebElement bmi_category =
                    driver.findElement(By.name("bmi_category"));
            assertEquals(bmi_category.getAttribute
                    ("value"),"Normal");

        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        Thread.sleep(5000);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
        String verificationErrorString =
                verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

    }
}

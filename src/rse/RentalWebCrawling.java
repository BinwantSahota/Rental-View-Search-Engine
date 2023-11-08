package rse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class RentalWebCrawling {
    public static void rentalWebCrawling(String url) {
        List<String> cityUrls = new ArrayList<>();
        Set<String> propertyUrls = new HashSet<>(); // Use a HashSet to store unique URLs

        // set system properties and driver options for Edge browser
        System.setProperty("webdriver.edge.driver", "C:\\Users\\Binwant Singh\\Desktop\\edgedriver_win64\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        EdgeDriver driver = new EdgeDriver(options);

        // navigate to the given URL
        driver.get(url);

        // find all city links on the page
        WebElement cityLinks = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/section[2]/div/ul"));
        List<WebElement> link = cityLinks.findElements(By.tagName("a"));

        // get URLs for all the cities
        for (WebElement lin : link) {
            cityUrls.add(lin.getAttribute("href"));
        }

        // navigate to each city URL and get rental property URLs
        for (String url1 : cityUrls) {
            driver.navigate().to(url1);

            // find and navigate to the rental property listings page for the city
            WebElement list = driver.findElement(By.cssSelector("#__layout > div > div > nav > ul > li:nth-child(2) a"));
            String listUrl = list.getAttribute("href");
            driver.navigate().to(listUrl);

            // find all the rental property URLs on the page
            WebElement propertyUrl = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div/div/div[2]"));
            List<WebElement> propertyLinks = propertyUrl.findElements(By.tagName("a"));

            // add each unique URL to the set of rental property URLs
            for (WebElement propUrl : propertyLinks) {
                if (propertyUrls.size() > 50) {
                    break;
                }

                String href = propUrl.getAttribute("href");
                // Exclude URLs containing "/contact.html" using a regular expression
                if (!href.matches(".*/contact\\.html$") && !propertyUrls.contains(href)) {
                    propertyUrls.add(href);
                    // save the html file of that url
                    URLsave.saveHtmlFile(href);
                }
            }
        }

        // close the driver once done
        driver.quit();
    }
}

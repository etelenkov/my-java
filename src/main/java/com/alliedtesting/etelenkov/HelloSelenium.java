package com.alliedtesting.etelenkov;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelloSelenium {
    public static void main(String[] args) throws InterruptedException {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html");
        WebElement element = driver.findElement(By.cssSelector(
            "#MainFlow > div:nth-child(3) > a:nth-child(2)"));
        element.click();
        Thread.sleep(5000);
        driver.close();
    }
}

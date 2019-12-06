package com.bogdansukonnov.eclinic.service;

import lombok.Getter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

@Getter
public class SeleniumConfig {

    private WebDriver driver;

    public SeleniumConfig() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    static {
        System.setProperty("webdriver.gecko.driver", "/home/bogdan/Apps/Selenium/geckodriver");
    }

}

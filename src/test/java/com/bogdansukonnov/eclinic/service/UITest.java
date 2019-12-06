package com.bogdansukonnov.eclinic.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UITest {

    private final static String url = "http://localhost:18080/";

    @Test
    void loginTest() {
        WebDriver driver = new SeleniumConfig().getDriver();
        driver.get(url);
        assert(!driver.getTitle().isEmpty());
        driver.close();
    }

}

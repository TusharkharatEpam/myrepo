package com.cleartrip.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.cleartrip.util.TestBaseClass;

public class ChromeDriverManager extends DriverManager {
	
	
	@Override
    public void createDriver() {
		String propertyValue=TestBaseClass.getProperty("chromeBinaryPath", "D:\\automation_workspace\\automation_workspace\\src\\test\\resources\\config\\");
        System.setProperty("webdriver.chrome.driver",propertyValue);
        driver = new ChromeDriver();

    }



}

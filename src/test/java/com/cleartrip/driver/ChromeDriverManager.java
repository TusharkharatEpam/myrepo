package com.cleartrip.driver;


import org.openqa.selenium.chrome.ChromeDriver;

import com.cleartrip.util.CleartripConstant;
import com.cleartrip.util.TestBaseClass;

public class ChromeDriverManager extends DriverManager {
	
	
	@Override
    public void createDriver() {
		String propertyValue=TestBaseClass.getProperty(CleartripConstant.CHROME_BINARY_PATH, CleartripConstant.CONFIG_PROPERTY_PATH);
        System.setProperty("webdriver.chrome.driver",propertyValue);
        driver = new ChromeDriver();

    }



}

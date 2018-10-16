package com.cleartrip.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	protected WebDriver driver;
	protected abstract void createDriver();


	 public WebDriver getDriver() {
		 if(null==driver) {
			 createDriver();
		 }
		 return driver;
	 }

}

package com.cleartrip.driver;

public class DriverManagerFactory {
	
	private DriverManagerFactory() {
		
	}
	public static DriverManager getManager(DriverType driverType) {
		DriverManager driverManager = null;
		switch (driverType) {
		case CHROME:
			driverManager=new ChromeDriverManager();
			break;
		default:
			break;
		}
		return driverManager;
	}

}

package com.cleartrip.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cleartrip.driver.TripType;
import com.cleartrip.util.CleartripConstant;
import com.cleartrip.util.ExelReaderUtil;
import com.cleartrip.util.TestBaseClass;

@Component
public class SearchFlightPage extends TestBaseClass {

	private static Logger log = Logger.getLogger(SearchFlightPage.class);

	@Autowired
	TestBaseClass testBaseClass;

	@FindBy(id = "OneWay")
	WebElement tripTypeOneWay;

	@FindBy(id = "RoundTrip")
	WebElement tripTypeRoundTrip;

	@FindBy(id = "MultiCity")
	WebElement tripTypeMultiCity;

	@FindBy(id = "FromTag")
	WebElement departFrom;

	@FindBy(name = "destination")
	WebElement destination;

	@FindBy(name = "adults")
	WebElement adults;

	@FindBy(id = "Childrens")
	WebElement childrens;

	@FindBy(id = "Infants")
	WebElement infants;

	@FindBy(id = "SearchBtn")
	WebElement searchFlightButton;

	@FindBy(id = "DepartDate")
	WebElement deparDate;

	@Autowired
	Environment env;

	WebDriver driver;
	Map<String, Object> excelDataMap = new HashMap<>();

	private String departDateString = "//td[@data-month=9]/child::a[text()='%s']";
	private String returnDateString = "//td[@data-month=9]/child::a[text()='%s']";

	public void init(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		try {
			excelDataMap = ExelReaderUtil.readExcel(env.getProperty(CleartripConstant.EXCEL_PATH),
					env.getProperty(CleartripConstant.EXCEL_NAME), env.getProperty(CleartripConstant.ONEWAY_SHEET));
		} catch (IOException e) {
			log.error("Error occured while reading the excel from given path ", e);
		}
	}

	public void loginToPortal(String URL) {
		log.info("Entering url into Browser====================>");
		driver.get(URL);
	}

	public void verifySelectedTripType(String tripType) {
		Boolean flag;
		log.info("tripType is====================> :" + tripType);
		if (TripType.MULTICITY.toString().equals(tripType)) {
			flag = tripTypeMultiCity.isSelected();
			selectTrip(flag, tripTypeMultiCity);
		} else if (TripType.ONEWAY.toString().equals(tripType)) {
			flag = tripTypeOneWay.isSelected();
			selectTrip(flag, tripTypeOneWay);
		} else if (TripType.ROUNDTRIP.toString().equals(tripType)) {
			flag = tripTypeRoundTrip.isSelected();
			selectTrip(flag, tripTypeRoundTrip);
		} else {
			log.info("Please select proper trip type");
		}
	}

	private void selectTrip(Boolean flag, WebElement tripTypeToBeSelected) {
		if (flag) {
			log.info("Already selected :" + tripTypeToBeSelected);
		} else {
			try {
				highlight(driver, tripTypeToBeSelected);
			} 
			catch (InterruptedException e) {
				log.log(Level.WARN, CleartripConstant.INTURRPTED, e);
			    Thread.currentThread().interrupt();

			}
			tripTypeToBeSelected.click();
		}
	}

	public void enteFlightrSourceAndDestination() {
		log.info("Inside method enteFlightrSourceAndDestination ");
		String src= (String) excelDataMap.get(CleartripConstant.SOURCE);
		if (null != src && !("").equalsIgnoreCase(src)) {
			try {
				highlight(driver, departFrom);
				departFrom.sendKeys(src);
			} catch (InterruptedException e) {
				log.log(Level.WARN,CleartripConstant.INTURRPTED, e);
			    Thread.currentThread().interrupt();

			}

		}
		String dest= (String) excelDataMap.get(CleartripConstant.DESTINATION);

		if (null != dest && !("").equalsIgnoreCase(dest)) {
			try {
				highlight(driver, destination);
				destination.sendKeys(dest);
			} catch (InterruptedException e) {
				log.log(Level.WARN, CleartripConstant.INTURRPTED, e);
			    Thread.currentThread().interrupt();

			}

		}

	}

	public void entersDetailsOfAdultChildrenAndInfants(String adult, String child, String infant) {
		log.info("Inside method entersDetailsOfAdultChildrenAndInfants ");
		testBaseClass.SelectUsingVisibleText(adults, adult);
		testBaseClass.SelectUsingVisibleText(childrens, child);
		testBaseClass.SelectUsingVisibleText(infants, infant);
	}

	public void click() {
		searchFlightButton.click();
	}

	public void enterFlightDate(String tripType) {
		log.info("Inside method enterFlightDate ");
		deparDate.click();
		departDateString = String.format(departDateString, 25);
		returnDateString = String.format(returnDateString, 25);
		driver.findElement(By.xpath(departDateString)).click();
		if (!TripType.ONEWAY.toString().equals(tripType)) {
			WebElement returnDate = driver.findElement(By.id("ReturnDate"));
			returnDate.click();
			driver.findElement(By.xpath(returnDateString)).click();
		}
	}

}

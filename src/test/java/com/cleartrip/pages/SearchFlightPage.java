package com.cleartrip.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	public SearchFlightPage() {

	}

	private static Logger log = Logger.getLogger(SearchFlightPage.class);

	@Autowired
	TestBaseClass testBaseClass;

	@FindBy(id = "OneWay")
	WebElement tripType_OneWay;

	@FindBy(id = "RoundTrip")
	WebElement tripType_RoundTrip;

	@FindBy(id = "MultiCity")
	WebElement tripType_MultiCity;

	@FindBy(id = "FromTag")
	WebElement departFrom;

	@FindBy(name = "destination")
	WebElement destination;

	@FindBy(name = "adults")
	WebElement adults;

	@FindBy(id = "Childrens")
	WebElement Childrens;

	@FindBy(id = "Infants")
	WebElement infants;

	@FindBy(id = "SearchBtn")
	WebElement searchFlightButton;

	@FindBy(id = "DepartDate")
	WebElement deparDate;

	@Autowired
	Environment env;

	WebDriver driver;
	Map<String, Object> excelDataMap = new HashMap<String, Object>();

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
			flag = tripType_MultiCity.isSelected();
			selectTrip(flag, tripType_MultiCity);
		} else if (TripType.ONEWAY.toString().equals(tripType)) {
			flag = tripType_OneWay.isSelected();
			selectTrip(flag, tripType_OneWay);
		} else if (TripType.ROUNDTRIP.toString().equals(tripType)) {
			flag = tripType_RoundTrip.isSelected();
			selectTrip(flag, tripType_RoundTrip);
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
			} catch (InterruptedException e) {
				log.error("Error Occured while highlighting :", e);
			}
			tripTypeToBeSelected.click();
		}
	}

	public void enteFlightrSourceAndDestination(String source, String dest) {
		log.info("Inside method enteFlightrSourceAndDestination ");
		if (null != excelDataMap.get("source") && !("").equalsIgnoreCase((String) excelDataMap.get("source"))) {
			try {
				highlight(driver, departFrom);
				departFrom.sendKeys((excelDataMap.get("source").toString()));
			} catch (InterruptedException e) {
				log.error("Error Occured while highlighting :", e);
			}

		}

		if (null != excelDataMap.get("destination")
				&& !("").equalsIgnoreCase((String) excelDataMap.get("destination"))) {

			try {
				highlight(driver, destination);
				destination.sendKeys(excelDataMap.get("destination").toString());
			} catch (InterruptedException e) {
				log.error("Error Occured while highlighting :", e);
			}

		}

	}

	public void entersDetailsOfAdultChildrenAndInfants(String adult, String child, String infant) {
		log.info("Inside method entersDetailsOfAdultChildrenAndInfants ");
		testBaseClass.SelectUsingVisibleText(adults, adult);
		testBaseClass.SelectUsingVisibleText(Childrens, child);
		testBaseClass.SelectUsingVisibleText(infants, infant);
	}

	public void click() {
		searchFlightButton.click();
	}

	public void enterFlightDate(String depart_Date, String return_Date, String tripType) {
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

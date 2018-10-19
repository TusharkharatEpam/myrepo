package com.cleartrip.steps;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cleartrip.driver.DriverManagerFactory;
import com.cleartrip.driver.DriverType;
import com.cleartrip.pages.SearchFlightPage;
import com.cleartrip.util.CleartripConstant;

@Component
public class SearchFlightOneWaySteps {

	static Logger log = Logger.getLogger(SearchFlightOneWaySteps.class);

	WebDriver driver;

	@Autowired
	SearchFlightPage searchFlightPage;

	Properties prop;

	@Autowired
	Environment env;

	@BeforeStory
	public void init() throws IOException {
		if (CleartripConstant.CHROME.equalsIgnoreCase(System.getProperty(CleartripConstant.BROWSER)))
			driver = DriverManagerFactory.getManager(DriverType.CHROME).getDriver();
		searchFlightPage.init(driver);
		driver.manage().window().maximize();
	}

	@When("enters the $departDate and $returnDate for $tripType")
	public void whenEntersTheDepartureDate(@Named("departDate") String departDate,
			@Named("returnDate") String returnDate, @Named("tripType") String tripType) {
		searchFlightPage.enterFlightDate(tripType);
	}

	@When("user enter flight $source and $destination")
	public void whenUserEnterFlightSourceAndDestination(@Named("source") String source,
			@Named("destination") String destination) {
		log.info("usser entering the source And Destination :" + source + " " + destination);
		searchFlightPage.enteFlightrSourceAndDestination();
	}

	@When("search for flights")
	public void whenSearchForOneWayFlights() {
		log.info("search Flight Button clicked");
		searchFlightPage.click();
	}

	@When("enters details of $adults $children And $infants")
	public void whenEntersDetailsOfAdultChildrenAndInfants(@Named("adults") String adult,
			@Named("children") String children, @Named("infants") String infants) {
		log.info("Entering the Child details");
		searchFlightPage.entersDetailsOfAdultChildrenAndInfants(adult, children, infants);

	}

	@Then("user should see all flights based on deftails provided")
	public void thenUserShouldSeeAllFlightsBasedOnDeftailsProvided() {
		log.info("User can now see the results");
	}

	@Given("user search for $tripType flights")
	public void givenUserSearchForOneWayFlighs(@Named("tripType") String tripType) {
		log.info("Searching for trip type :" + tripType);
		searchFlightPage.verifySelectedTripType(tripType);
	}

	@Given("user open the cleartrip portal")
	public void givenUserOpenTheCleartripPortal() {
		log.info("CLear trip Portal Opening in browser");
		searchFlightPage.loginToPortal(env.getProperty(CleartripConstant.WEB_URL));
	}

	@AfterStory
	public void destroy() {
		driver.close();
	}
}
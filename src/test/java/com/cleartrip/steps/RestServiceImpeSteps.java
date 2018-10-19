package com.cleartrip.steps;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cleartrip.rest.RestServiceUtility;
import com.cleartrip.util.CleartripConstant;

import io.restassured.response.Response;
@Component
public class RestServiceImpeSteps{
	
	private Logger log=Logger.getLogger(RestServiceImpeSteps.class);
	
	@Autowired 
    private Environment env;
	
	@Autowired
	private RestServiceUtility restServiceUtility;
	Response response;
	@When("user hit the rest service with parameters $trip_type $source $destination  $depart_date  $adults $children $infants $origin $from $to $class $ver $type")
	public void whenUserHitTheRestServiceWithParametersTrip_typeSourceDestinationDepart_dateAdultsChildrenInfantsOriginFromToClassVerType(
			@Named("trip_type") String trip_type,@Named("source") String source,@Named("destination") String destination,
			@Named("depart_date") String depart_date,@Named("adults") String adults,@Named("children") String children,
			@Named("infants") String infants,@Named("origin") String origin,@Named("from") String from,
			@Named("to") String to,@Named("class") String class1,@Named("ver") String ver,@Named("type") String type){
				
		 Map<String, String> restMap = getMapObjForRest(trip_type, destination, depart_date, adults, children, infants,
				origin, from, to, class1, ver, type);
		 String url=env.getProperty(CleartripConstant.CLEARTRIP_ONEWAY_URL);
		 response=restServiceUtility.getRequest(url,restMap);
		
	}

	private Map<String, String> getMapObjForRest(String trip_type, String destination, String depart_date,
			String adults, String children, String infants, String origin, String from, String to, String class1,
			String ver, String type) {
		Map<String, String> restMap=new HashMap<String, String>();
		restMap.put("trip_type", trip_type);
		restMap.put("origin", origin);
		restMap.put("from",from);
		restMap.put("destination",destination);
		restMap.put("to", to);
		restMap.put("depart_date", depart_date);
		restMap.put("adults",adults);
		restMap.put("childs", children);
		restMap.put("infants", infants);
		restMap.put("class",class1);
		restMap.put("ver", ver);
		restMap.put("type", type);
		return restMap;
	}

	@Then("validate the response")
	public void thenValidateTheResponse(){
		log.info("Reponse is :"+response.asString());
		response.then().assertThat().statusCode(200);
		
	}
}
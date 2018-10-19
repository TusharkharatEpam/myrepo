package com.cleartrip.steps;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cleartrip.rest.RestServiceUtility;
import com.cleartrip.util.CleartripConstant;
import com.cleartrip.util.ExelReaderUtil;

import io.restassured.response.Response;
@Component
public class RestServiceImpeSteps{
	
	Map<String, Object> excelDataMap = new HashMap<>();
	
	private Logger log=Logger.getLogger(RestServiceImpeSteps.class);
	
	@Autowired 
    private Environment env;
	
	@Autowired
	private RestServiceUtility restServiceUtility;
	Response response;
	@When("user hit the rest service with parameters $trip_type $source $destination  $depart_date  $adults $children $infants $origin $from $to $class $ver $type")
	public void getResponseOfCleartripService() throws IOException{
		excelDataMap = ExelReaderUtil.readExcel(env.getProperty(CleartripConstant.EXCEL_PATH),
				env.getProperty(CleartripConstant.EXCEL_NAME),CleartripConstant.RESTSERVICE_CLEARTRIP);
				
		 Map<String, String> restMap = getMapObjForRest();
		 String url=env.getProperty(CleartripConstant.CLEARTRIP_ONEWAY_URL);
		 response=restServiceUtility.getRequest(url,restMap);
		log.info("Response is =================>:"+response.asString());
		
	}

	private Map<String, String> getMapObjForRest() {
		Map<String, String> restMap=new HashMap<>();
		restMap.put("trip_type", (String) excelDataMap.get("trip_type"));
		restMap.put("origin", (String) excelDataMap.get("origin"));
		restMap.put("from",(String) excelDataMap.get("from"));
		restMap.put("destination",(String) excelDataMap.get("destination"));
		restMap.put("to", (String) excelDataMap.get("to"));
		restMap.put("depart_date", (String) excelDataMap.get("depart_date"));
		restMap.put("adults",(String) excelDataMap.get("adults"));
		restMap.put("childs", (String) excelDataMap.get("children"));
		restMap.put("infants", (String) excelDataMap.get("infants"));
		restMap.put("class",(String) excelDataMap.get("class1"));
		restMap.put("ver", (String) excelDataMap.get("ver"));
		restMap.put("type", (String) excelDataMap.get("type"));
		return restMap;
	}

	@Then("validate the response")
	public void thenValidateTheResponse(){
		log.info("Reponse is :"+response.asString());
		response.then().assertThat().statusCode(200);
		
	}
}
package com.cleartrip.rest;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Component
public class RestServiceUtility {

	
	public Response getRequest(String baseUri, Map<String, String> queryparams)
	{
		return RestAssured.given().queryParams(queryparams).get(baseUri);
		
		
	}

	
	
}

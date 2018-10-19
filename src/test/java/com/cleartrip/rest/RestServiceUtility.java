package com.cleartrip.rest;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Component
public class RestServiceUtility {

	
	public Response getRequest(String baseUri, Map<String, String> queryparams)
	{
		RestAssured.baseURI = baseUri;
		RequestSpecification httpRequest = RestAssured.given().queryParams(queryparams);
		Response response = httpRequest.get();
		return response;
	}

	
	
}

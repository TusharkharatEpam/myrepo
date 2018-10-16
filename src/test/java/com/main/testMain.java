package com.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.cleartrip.rest.RestServiceUtility;
import com.cleartrip.util.ExelReaderUtil;

import io.restassured.response.Response;

public class testMain {

	public static void main(String[] args) {
		ExelReaderUtil excelRead= new ExelReaderUtil();
		/*DriverManager manager = DriverManagerFactory.getManager(DriverType.FIREFOX);
		WebDriver driver = manager.getDriver();
		driver.get("https://www.facebook.com/");*/
		
		
		/*try {
			Map<String ,Object> mm=ExelReaderUtil.readExcel("D:\\automation_workspace\\automation_workspace\\src\\test\\resources\\data\\", "cleartrip_testdata.xlsx", "searchFlight_OneWay");
			System.out.println("=======>return date :"+mm.get("returnDate"));
			//excelRead.readExcel("D:\\automation_workspace\\automation_workspace\\src\\test\\resources\\data\\", "cleartrip_testdata.xlsx", "searchFlight_OneWay");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		RestServiceUtility rest= new RestServiceUtility();
		Map<String,Object> restMap=new HashMap<String, Object>();
		restMap.put("trip_type", "OneWay");
		restMap.put("origin", "Pune%2C+IN+-+Lohegaon+(PNQ)");
		restMap.put("from", "PNQ");
		restMap.put("destination", "Mumbai IN Chatrapati Shivaji Airport (BOM)");
		restMap.put("to", "BOM");
		restMap.put("depart_date", "25/10/2018");
		restMap.put("adults", "1");
		restMap.put("childs", "0");
		restMap.put("infants", "0");
		restMap.put("class", "Economy");
		restMap.put("ver", "V2");
		restMap.put("type", "json");
		Response response=rest.getRequest("https://www.cleartrip.com/flights/results/airjson",restMap);
		if(response.getStatusCode()==200) {
			System.out.println("==========PASSED=====");
		}
		else {
			System.out.println("==========FAILED=====");
		}
		System.out.println("Reponse is :"+response.asString());
	}

}

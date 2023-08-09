package com.restassured.api.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import org.testng.annotations.Ignore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SWAPITest {
	private static final Logger log = LogManager.getLogger(SWAPITest.class);
	private final static String BASEURI = "https://swapi.dev/api/"; //1
	private final static String BASEPATH = "people";
	
	@Test	
	public void statusTest() {	
		log.info("Status Test Running");		
		Response response = 	
		RestAssured	
		.given()
			.contentType(ContentType.JSON)	
			.baseUri(BASEURI+"people")	
		.when()
			.get()
		.then()
			.assertThat()
			.statusCode(200)
			.statusLine("HTTP/1.1 200 OK")
			.header("Content-Type", "application/json")
		.extract()
			.response();		
	}
	@Test
	public void nameTest() {
		Response response = 	
				RestAssured
				.given()
					.contentType(ContentType.JSON)	
					.baseUri(BASEURI+BASEPATH)	
				.when()
					.get()
				.then()
					.assertThat()
					.statusCode(200)
					.statusLine("HTTP/1.1 200 OK")
					.header("Content-Type", "application/json")
				.extract()
					.response();				
		
			// Convert JSON object to String object
			JsonPath jsonResponse = new JsonPath(response.asString());
		
		// Get values of JSON array after getting array size	
		int jsonArraySize = jsonResponse.getInt("results.size()");
		List<String> nameList = new ArrayList<String>();
		
		for(int i = 0; i< jsonArraySize;i++){
			String height = jsonResponse.getString("results["+i+"].height");
			
			// convert string to int 
			int stringToInt = Integer.parseInt(height);
			log.info("Height: "+stringToInt);
			// Check if condition meets criteria
			if(stringToInt > 200) {
				nameList.add(jsonResponse.getString("results["+i+"]")); // store complete object
				log.info("Object Stored!!!");			
			}
			else {
				log.info("Doesn't Match...");
			}		
			
		}
		log.info(nameList);
		
		// convert ArrayList to Json
		Gson gson = new Gson();
		String jsonConvert = gson.toJson(nameList);
		
		Assert.assertTrue(jsonConvert.contains("Darth Vader"));
	}
	
}

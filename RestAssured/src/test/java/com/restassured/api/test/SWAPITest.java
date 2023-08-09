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
}

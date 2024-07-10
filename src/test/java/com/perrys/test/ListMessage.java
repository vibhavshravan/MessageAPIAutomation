package com.perrys.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ListMessage extends GetMessage {
	
	private String fromId = null;
	private String toId = null;
	private ArrayList <String> messageId = new ArrayList <String>();
	
	//Function to validate if list of messages sent by user with toId to user with fromId can be retrieved
	@Test
	public void getListToFrom () throws JsonProcessingException {
		System.out.println("Starting Test12");
		fromId = getfromId();
		toId = gettoId();
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("?from="+toId+"&to="+fromId);
		response.then().statusCode(200);
		System.out.println(response.getBody().asPrettyString());
		Object messageList1 = response.as(List.class).get(1);
		Object messageList2 = response.as(List.class).get(0);
		ObjectMapper mapper = new ObjectMapper();
		String strResponse1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageList1);
		String strResponse2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageList2);
		JsonPath json1 = JsonPath.from(strResponse1);
		JsonPath json2 = JsonPath.from(strResponse2);
		String id1 = json1.getString("id");
		String id2 = json2.getString("id");
		Assert.assertEquals(id1 ,messageId.get(2));
		Assert.assertEquals(id2 ,messageId.get(3));
	}
	
	//Function to validate if list of messages sent by user with fromId to user with toId can be retrieved
	@Test
	public void getListFromTo() throws JsonProcessingException {
		System.out.println("Starting Test13");
		fromId = getfromId();
		toId = gettoId();
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("?from="+fromId+"&to="+toId);
		response.then().statusCode(200);
		System.out.println(response.getBody().asPrettyString());
		Object messageList = response.as(List.class).get(0);
		ObjectMapper mapper = new ObjectMapper();
		String strResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageList);
		JsonPath json = JsonPath.from(strResponse);
		String id = json.getString("id");
		Assert.assertEquals(id ,messageId.get(0));
	}
	
	//Function to validate error response when no message are sent by user with fromId to user with toId
	@Test
	public void validateNoMessageBetweenFromTo() throws JsonProcessingException {
		System.out.println("Starting Test14");
		fromId = getfromId();
		toId = gettoId();
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("?from="+fromId+"&to=402e760d-24da-4deb-8f32-6480638ab886");
		if(response.getStatusCode() != 404) {
			System.out.println(response.getBody().asPrettyString());
		}
		response.then().statusCode(404);		
	}
	
	//Function to validate error response if either of fromId or toId is empty
	@Test
	public void validateEitherFromOrToAsBlank() throws JsonProcessingException {
		System.out.println("Starting Test15");
		fromId = getfromId();
		toId = gettoId();
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("?from="+fromId+"&to=");
		if(response.getStatusCode() != 404) {
			System.out.println(response.getBody().asPrettyString());
		}
		response.then().statusCode(404);
	}

}

package com.perrys.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.perrys.library.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateMessage extends ReusableMethods{
	
	//Test Class to validate Create Message API
	
	private String fromId = null;
	private String toId = null;
	private static String request=null;
	private ArrayList <String> messageId = new ArrayList <String>();
	
	public String getfromId() {
		return fromId;
	}
	public void setfromId(String fromId) {
		this.fromId = fromId;
	}
	public String gettoId() {
		return toId;
	}
	public void settoId(String toId) {
		this.toId = toId;
	}
	public ArrayList<String> getMessageId() {
		return messageId;
	}
	public void setMessageId(ArrayList<String> messageId) {
		this.messageId = messageId;
	}
	//Function to validate if message can be sent from user with fromId to user with toId
	@Test
	public void sendMessage() throws Exception {
		System.out.println("Starting Test1");
		fromId = getFromId();
		toId = getToId();
		request = createRequest(fromId, toId);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(200);
		messageId.add(response.then().extract().path("id").toString());
		Assert.assertNotNull(messageId.get(0));
	}
	
	//Function to validate if a reply can be sent back by user with toId to user with fromId
	@Test
	public void sendReplyMessage() throws Exception {
		System.out.println("Starting Test2");
		fromId = getFromId();
		toId = getToId();
		request = createRequest(toId, fromId);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(200);
		messageId.add(response.then().extract().path("id").toString());
		Assert.assertNotNull(messageId.get(1));
	}
	
	//Function to validate if consecutive messages can be sent by the same user to the same receiver
	@Test
	public void sendReplyMessageAgain() throws Exception {
		System.out.println("Starting Test3");
		fromId = getFromId();
		toId = getToId();
		request = createRequest(toId, fromId);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(200);
		messageId.add(response.then().extract().path("id").toString());
		Assert.assertNotNull(messageId.get(2));
		
		request = createRequest(toId, fromId);
		Response response2 = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response2.asPrettyString());						
		response2.then().statusCode(200);
		messageId.add(response2.then().extract().path("id").toString());
		Assert.assertNotNull(messageId.get(3));
	}
	
	//Function to validate error response when fromId is null
	@Test
	public void validateErrorForFromId() throws Exception {
		System.out.println("Starting Test4");
		fromId = getFromId();
		toId = getToId();
		request = createRequest(null, toId);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(400);
		String errorMessage = response.then().extract().path("error.message");
		String errorName = response.then().extract().path("error.name");
		Assert.assertEquals("should be string", errorMessage);
		Assert.assertEquals("ServiceError", errorName);
	}
	
	//Function to validate error response when toId is null
	@Test
	public void validateErrorForToId() throws Exception {
		fromId = getFromId();
		toId = getToId();
		System.out.println("Starting Test5");
		request = createRequest(fromId, null);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(400);
		String errorMessage = response.then().extract().path("error.message");
		String errorName = response.then().extract().path("error.name");
		Assert.assertEquals("should be string", errorMessage);
		Assert.assertEquals("ServiceError", errorName);
	}
	
	//Function to validate error response when message is null
	@Test
	public void validateErrorForMessage() throws Exception {
		System.out.println("Starting Test6");
		fromId = getFromId();
		toId = getToId();
		request = createRequestWithoutMessage(fromId, toId);
		Response response = RestAssured.given()
								.baseUri("http://localhost:3000/api/messages")
								.contentType("application/json")
								.body(request)
								.post();
		System.out.println(response.asPrettyString());						
		response.then().statusCode(400);
		String errorMessage = response.then().extract().path("error.message");
		String errorName = response.then().extract().path("error.name");
		Assert.assertEquals("should be string", errorMessage);
		Assert.assertEquals("ServiceError", errorName);
	}
	

}

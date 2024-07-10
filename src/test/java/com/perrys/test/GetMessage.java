package com.perrys.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetMessage extends DeleteMessage {
	
	private ArrayList <String> messageId = new ArrayList <String>();
	
	//Function to validate if a message can be retrieved using messageId
	@Test
	public void getUsingMessageId () {
		System.out.println("Starting Test9");
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("/"+messageId.get(0));
		response.then().statusCode(200);
		System.out.println(response.asPrettyString());
		String exFromId = getfromId();
		String exToId = gettoId();
		String from_Id = response.then().extract().path("from.id");
		String to_Id = response.then().extract().path("to.id");
		String message_Id = response.then().extract().path("id");
		Assert.assertEquals(exFromId, from_Id);
		Assert.assertEquals(exToId, to_Id);
		Assert.assertEquals(message_Id, messageId.get(0));
	}
	
	//Function to validate error response when a deleted message is retrieved using messageId
	@Test
	public void validateGetDeletedMessageId () {
		System.out.println("Starting Test10");
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("/"+messageId.get(1));
		response.then().statusCode(404);
	}
	
	//Function to validate error response when a messageId used for retrieval is invalid/non existent
	@Test
	public void validateInvalidMessageId () {
		System.out.println("Starting Test11");
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.get("/fhjsi894yik78");
		response.then().statusCode(404);
	}

}

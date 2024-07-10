package com.perrys.test;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteMessage extends CreateMessage{
	
	private ArrayList <String> messageId = new ArrayList <String>();
	
	//Function to validate if a message can be deleted using messageId
	@Test
	public void deleteUsingMessageId () {
		System.out.println("Starting Test7");
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.delete("/"+messageId.get(1));
		response.then().statusCode(204);
		String strResponse = response.body().asPrettyString();
		Assert.assertEquals(strResponse,"");
	}
	
	//Function to validate error response when trying to delete a previously deleted message 
	//@Test
	public void validateDeletedMessageId () {
		System.out.println("Starting Test8");
		messageId = getMessageId();
		Response response = RestAssured.given()
				.baseUri("http://localhost:3000/api/messages")
				.contentType("application/json")
				.delete("/"+messageId.get(1));
		response.then().statusCode(404);
		String strResponse = response.body().asPrettyString();
		System.out.println(strResponse);
		String message = response.then().extract().path("error.message");
		Assert.assertEquals(message, "message not found");
	}

}

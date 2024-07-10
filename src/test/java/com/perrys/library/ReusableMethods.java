package com.perrys.library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perrys.pojo.From;
import com.perrys.pojo.InputRequest;
import com.perrys.pojo.To;

public class ReusableMethods {

	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}

	/* Function to generate Random texts for messages. Number of characters per
	 message is given as the control for while() loop*/
	public String RandomMessage() {
		String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		while (sb.length() < 15) {
			int index = (int) (rnd.nextFloat() * CHARS.length());
			sb.append(CHARS.charAt(index));
		}
		String randomMsg = sb.toString();
		return randomMsg;
	}

	private static String fromId = null;
	private static String toId = null;

	public static String getFromId() {
		return fromId;
	}

	public static void setFromId(String fromId) {
		ReusableMethods.fromId = fromId;
	}

	public static String getToId() {
		return toId;
	}

	public static void setToId(String toId) {
		ReusableMethods.toId = toId;
	}

	//Creating two new user IDs for testing before execution starts
	@BeforeSuite
	public void setFromAndToId() {
		setFromId(UUID.randomUUID().toString());
		setToId(UUID.randomUUID().toString());
	}

	//Function to create request payload for Create Message POST method
	public String createRequest(String fromId, String toId) throws Exception {

		String requestPayload = null;
		
		//Objects for POJO class
		InputRequest inputRequest = new InputRequest();
		From from = new From();
		To to = new To();

		//Setting values for POJO class
		from.setId(fromId);
		to.setId(toId);
		inputRequest.setFrom(from);
		inputRequest.setTo(to);
		String message = RandomMessage();
		inputRequest.setMessage(message);

		//Converting POJO to String
		ObjectMapper mapper = new ObjectMapper();
		requestPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputRequest);
		
		//Capturing request in a text file
		recordRequestResponse(requestPayload);

		return requestPayload;
	}

	//Above function duplicated to send message as null
	public String createRequestWithoutMessage(String fromId, String toId) throws Exception {

		String requestPayload = null;
		InputRequest inputRequest = new InputRequest();
		From from = new From();
		To to = new To();

		from.setId(fromId);
		to.setId(toId);

		inputRequest.setFrom(from);
		inputRequest.setTo(to);
		inputRequest.setMessage(null);

		ObjectMapper mapper = new ObjectMapper();
		requestPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputRequest);
		recordRequestResponse(requestPayload);

		return requestPayload;
	}

	//Function to capture Request/Response in a text file
	public static void recordRequestResponse(String payload) throws IOException {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String timeStamp = currentTime.format(f);
		File file = new File(getProjectPath() + "\\src\\Requests_Responses\\" + timeStamp + ".txt");
		FileWriter fw = new FileWriter(file);
		fw.write(payload);
		fw.close();
	}

}

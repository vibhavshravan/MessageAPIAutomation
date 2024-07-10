package com.perrys.pojo;

public class InputRequest {
	
	/*POJO class for the entire JSON
	 * 
	 * {
    "from": {
        "id": "fromUserId"
    },
    "to": {
        "id": "toUserId"
    },
    "message": "text content of the message",
    "id": "uuid-of-message",
    "time": "2021-03-04T00:54:30.288Z"
   }
	 */

	private From from;
	private To to;
	private String message;

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

	public To getTo() {
		return to;
	}

	public void setTo(To to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;

	}

}

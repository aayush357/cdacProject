package com.response;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponse {

    private String message;
    private List<String> messages;
    private LocalDateTime dateTime;
    
    public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }    
}
package com.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiReponse {
    private boolean success;
    private String message;

    public ApiReponse(boolean success, String message) {
    	this.setMessage(message);
    	this.setSuccess(success);
    }

}
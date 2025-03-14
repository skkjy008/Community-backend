package com.example.vueProject.domain;

import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.*;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterRes<T> {
	
	private int statusCode;
    private String responseMessage;
    private T data;
    
    public MasterRes(final int statusCode, final String reponseMessage)
    {
    	this.statusCode = statusCode;
    	this.responseMessage = reponseMessage;
    	
    }
    
    public MasterRes(int statusCode, String responseMessage, T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }
    
    
    public int getStatusCode() { return statusCode; }
    public String getResponseMessage() { return responseMessage; }
    public T getData() { return data; }

}

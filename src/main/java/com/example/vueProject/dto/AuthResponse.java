package com.example.vueProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	
    private int statusCode;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String username;
    
	public AuthResponse(int statusCode, String message, String accessToken, String refreshToken) {
        this.statusCode = statusCode;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
	
	public AuthResponse(int statusCode, String message, String accessToken, String refreshToken, String nickname, String username) {
        this.statusCode = statusCode;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.setNickname(nickname);
        this.setUsername(username);
    }
    
    public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


    
}

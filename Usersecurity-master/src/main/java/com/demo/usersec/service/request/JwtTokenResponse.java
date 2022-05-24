package com.demo.usersec.service.request;

import lombok.Data;

@Data
public class JwtTokenResponse {

	
	private final String token;
	
	private final String fullName;

    public JwtTokenResponse(String token, String fullName) {
        this.token = token;
		this.fullName = fullName;
    }

}

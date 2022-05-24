package com.demo.usersec.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 104761578659477198L;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
	
	@Value("${jwt.caller.ip}")
	private String callerIp;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		logger.debug("Auth Exception - unauthorized");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.setHeader("Access-Control-Allow-Origin", callerIp);
		//System.out.println("ssresp " + response.toString() );
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Unauthorized - No Jwt token or token has expired");
	}
}

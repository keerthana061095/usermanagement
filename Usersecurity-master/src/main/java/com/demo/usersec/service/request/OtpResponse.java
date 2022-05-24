package com.demo.usersec.service.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class OtpResponse implements Serializable {

	private static final long serialVersionUID = 3872186897854039390L;

	String username;
	String otp;
}

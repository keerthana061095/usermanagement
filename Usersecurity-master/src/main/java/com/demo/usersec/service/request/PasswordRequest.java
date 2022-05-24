package com.demo.usersec.service.request;

import org.springframework.lang.Nullable;

public class PasswordRequest {

	private String userName;
	private String OTP;
	private String currentPassword;
	private String newPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String OTP) {
		this.OTP = OTP;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordRequest [userName=" + userName + ", OTP=" + OTP + ", currentPassword=" + currentPassword
				+ ", newPassword=" + newPassword + "]";
	}

}

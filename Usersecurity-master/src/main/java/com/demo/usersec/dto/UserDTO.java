package com.demo.usersec.dto;

import java.util.HashSet;
import java.util.Set;

import com.demo.usersec.model.Role;

import lombok.Data;

@Data
public class UserDTO {

	private Long userUid;
	private String fullName;
	private String userName;
	private String password;
	private String email;
	private Long contactNumber;
	private Long faxNumber;
	private int passcodeVerify;
	private Set<Role> roles = new HashSet<>();

}

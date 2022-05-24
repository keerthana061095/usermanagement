package com.demo.usersec.dto;

import com.demo.usersec.model.User;

import lombok.Data;

@Data
public class UserRequestDto {

	private User user;
	private Integer active;
}

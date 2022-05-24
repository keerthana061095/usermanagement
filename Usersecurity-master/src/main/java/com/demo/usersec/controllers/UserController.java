package com.demo.usersec.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usersec.model.User;
import com.demo.usersec.model.UserRoleMap;
import com.demo.usersec.service.UserService;
import com.demo.usersec.service.request.OtpResponse;
import com.demo.usersec.service.request.PasswordRequest;
import com.demo.usersec.service.request.UserRequest;

@RestController
@RequestMapping(path = "v1/user")
public class UserController {

	@Autowired
	UserService userService;

	

	@Transactional
	@PostMapping(path = "/save", consumes = "application/json")
	public UserRequest saveUserData(@RequestBody UserRequest request) {

		User user = new User();
		user = userService.addUser(request, user);

		UserRoleMap userRoleMap = new UserRoleMap();

		userRoleMap = userService.mapUserRole(request, user, userRoleMap);

		UserRequest response = userService.buildResponse(userRoleMap);

		return response;

	}

	@Transactional
	@PutMapping("/resetPassword")
	public ResponseEntity<Object> resetPassword(@RequestBody PasswordRequest request) {
		if (userService.isOtpValid(request)) {

			userService.updatePassword(request);

			return new ResponseEntity<Object>("Password reset ", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Invalid OTP " + request.getUserName(), HttpStatus.UNAUTHORIZED);
		}

	}

	// send OTP to user
	@Transactional
	@PostMapping("/generateOtp/{userName}")
	public OtpResponse generateOtp(@PathVariable("userName") String userName) {

		return userService.generateCode(userName);

	}

	@Transactional
	@PutMapping("/changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody PasswordRequest request) {

		if (userService.isPasswordValid(request)) {
			userService.updatePassword(request);
			return new ResponseEntity<Object>("Password changed ", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Invalid password " + request.getUserName(), HttpStatus.UNAUTHORIZED);
		}

	}

}

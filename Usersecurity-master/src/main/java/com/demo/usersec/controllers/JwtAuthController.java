package com.demo.usersec.controllers;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usersec.jwt.JwtTokenUtil;
import com.demo.usersec.service.JwtUserDetailsService;
import com.demo.usersec.service.request.JwtTokenRequest;
import com.demo.usersec.service.request.JwtTokenResponse;
import com.demo.usersec.service.request.UserRequest;

@RestController
@CrossOrigin
@RequestMapping("v1")
public class JwtAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Transactional
	@PostMapping(path = "/authenticate", consumes = "application/json")
	public JwtTokenResponse createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
			throws Exception {

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);
		final String fullName = jwtUserDetailsService.getFullName();

		return new JwtTokenResponse(token, fullName);
	}

	@Transactional
	@PostMapping(path = "/register", consumes = "application/json")

//	@RequestMapping(value = "${jwt.register.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserRequest user) throws Exception {
		System.out.println("Registration: " + user.getUserName());
		return ResponseEntity.ok(jwtUserDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}

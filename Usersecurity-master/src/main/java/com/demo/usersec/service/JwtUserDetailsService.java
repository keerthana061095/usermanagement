package com.demo.usersec.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.demo.usersec.model.User;
import com.demo.usersec.service.request.UserRequest;

@Service
public interface JwtUserDetailsService extends UserDetailsService {


	@Override
	public UserDetails loadUserByUsername(String username);

	public String getFullName();

	public User save(UserRequest userReq);

}

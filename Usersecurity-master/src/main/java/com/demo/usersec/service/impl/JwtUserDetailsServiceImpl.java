package com.demo.usersec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.demo.usersec.jwt.JwtUserDetails;
import com.demo.usersec.model.Role;
import com.demo.usersec.model.User;
import com.demo.usersec.model.UserRoleMap;
import com.demo.usersec.repository.RoleRepository;
import com.demo.usersec.repository.UserRepository;
import com.demo.usersec.repository.UserRoleRepository;
import com.demo.usersec.service.JwtUserDetailsService;
import com.demo.usersec.service.request.UserRequest;

@Component
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRoleRepository userRoleRepo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	private String fullName;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUserNameIgnoreCase(username);

		UserRoleMap userRoleMap = userRoleRepo.findByUserUid(user.getUserUid());

		if (userRoleMap.getActive() != 0) {
			fullName = user.getFullName();
		}
		
		return new JwtUserDetails(user);

	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public User save(UserRequest userReq) {
		User newUser = new User();
		newUser.setUserName(userReq.getUserName().toLowerCase());
		newUser.setPassword(bcryptEncoder.encode(userReq.getPassword()));
		newUser.setEmail(userReq.getEmail());
		newUser.setFullName(userReq.getFullName());
		newUser.setPasscodeVerify(userReq.getPasscodeVerify());
		System.out.println("User repo " + newUser.toString());

		User user = userRepo.save(newUser);

		Role role = roleRepository.findById(userReq.getRoleUid()).orElseThrow(null);
		System.out.println(role.getRoleName());

		UserRoleMap roleMap = new UserRoleMap();
		roleMap.setActive(1);
		System.out.println("roleMap.getActive() :" + roleMap.getActive());
		roleMap.setRole(role);
		roleMap.setUser(user);
		userRoleRepo.save(roleMap);
		return user;
	}

}

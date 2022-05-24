package com.demo.usersec.service;

import java.util.List;

import com.demo.usersec.model.Role;

public interface RoleService {

	public List<Role> getAllRole();

	Role createUpdate(Role role);

}

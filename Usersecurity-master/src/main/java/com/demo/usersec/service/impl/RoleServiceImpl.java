package com.demo.usersec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.usersec.model.Role;
import com.demo.usersec.repository.RoleRepository;
import com.demo.usersec.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepo;

	@Override
	public List<Role> getAllRole() {
		return roleRepo.findAll();
	}

	@Override
	public Role createUpdate(Role roleReq) {

		Role role = roleRepo.save(roleReq);
		return role;
	}

}

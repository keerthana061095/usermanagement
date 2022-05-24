package com.demo.usersec.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usersec.model.Role;
import com.demo.usersec.service.RoleService;

@RestController
@RequestMapping(path = "v1/role")
public class RolesController {

	@Autowired
	RoleService roleService;

	@GetMapping(path = "/getAll")
	public List<Role> getAllRoles(Long id) {
		List<Role> roleList = roleService.getAllRole();

		return roleList;
	}

	@PostMapping(path = "/save")
	public Role saveRoleData(@RequestBody Role data) {
		Role role = roleService.createUpdate(data);

		return role;
	}

}

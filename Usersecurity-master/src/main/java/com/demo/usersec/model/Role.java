package com.demo.usersec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RoleUID", nullable = false)
	Long roleUID;

	@Column(name = "RoleName", nullable = false)
	String roleName;

	@Column(name = "DefaultScreenUID", nullable = false)
	String defaultScreenUID;

	@Column(name = "active", nullable = false)
	int active;

}

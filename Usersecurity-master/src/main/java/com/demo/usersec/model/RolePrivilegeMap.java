package com.demo.usersec.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "RolePrivilegeMap")
public class RolePrivilegeMap {

	private static final long serialVersionUID = 5196156176040203756L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RolePrivilegeUID")
	private Long rolePrivilegeUid;

	@ManyToOne
	@JoinColumn(name = "RoleUID")
	private Role role;

}

package com.demo.usersec.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "UserList")
@NoArgsConstructor
public class User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User(Long userUid) {
		super();
		this.userUid = userUid;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserUID")
	Long userUid;

	@Column(name = "FullName", nullable = false)
	private String fullName;

	@Column(name = "UserName", nullable = false)
	String userName;

	@JsonIgnore
	@Column(name = "Password", nullable = false)
	String password;

	@Column(name = "Email", nullable = false)
	String email;

	@Column(name = "PasscodeVerify", nullable = false)
	int passcodeVerify;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "UserRoleMap", joinColumns = @JoinColumn(name = "UserUID", referencedColumnName = "UserUID"), inverseJoinColumns = @JoinColumn(name = "RoleUID", referencedColumnName = "RoleUID"))
	private Set<Role> roles = new HashSet<>();

}

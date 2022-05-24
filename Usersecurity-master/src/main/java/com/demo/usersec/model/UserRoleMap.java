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

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "UserRoleMap")
public class UserRoleMap {

	private static final long serialVersionUID = -8964676508142079511L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="UserRoleUID")
	private Long userRoleUid;
	
	@ManyToOne
	@JoinColumn(name="UserUID")
	User user;

	@ManyToOne
	@JoinColumn(name="RoleUID")
	Role role;

	@Column(name = "active")
	private Integer active;

}

package com.demo.usersec.service.request;

import org.springframework.lang.Nullable;

import com.demo.usersec.model.Role;

public class UserRequest {

	private Long userUid;
	private String fullName;
	private String userName;
	private String password;
	private String email;
	@Nullable
	private Long contactNumber;
	@Nullable
	private Long faxNumber;
	private Integer passcodeVerify;
	private Integer active;
	
	private Long roleUid = new Role().getRoleUID();
			
	
	private String roleName = new Role().getRoleName();
	public Long getUserUid() {
		return userUid;
	}
	public void setUserUid(Long userUid) {
		this.userUid = userUid;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Long getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(Long faxNumber) {
		this.faxNumber = faxNumber;
	}
	public Integer getPasscodeVerify() {
		return passcodeVerify;
	}
	public void setPasscodeVerify(Integer passcodeVerify) {
		this.passcodeVerify = passcodeVerify;
	}
	public Long getRoleUid() {
		return roleUid;
	}
	public void setRoleUid(Long roleUid) {
		this.roleUid = roleUid;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "UserRequest [userUid=" + userUid + ", fullName=" + fullName + ", userName=" + userName + ", password="
				+ password + ", email=" + email + ", contactNumber=" + contactNumber + ", faxNumber=" + faxNumber
				+ ", passcodeVerify=" + passcodeVerify + ", roleUid=" + roleUid + ", active=" + active + ", roleName="
				+ roleName + "]";
	}

	
}

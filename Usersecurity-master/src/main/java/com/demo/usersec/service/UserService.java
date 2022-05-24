package com.demo.usersec.service;

import java.util.List;

import com.demo.usersec.model.User;
import com.demo.usersec.model.UserRoleMap;
import com.demo.usersec.service.request.OtpResponse;
import com.demo.usersec.service.request.PasswordRequest;
import com.demo.usersec.service.request.UserRequest;

public interface UserService {
	public UserRoleMap mapUserRole(UserRequest request, User user, UserRoleMap userRoleMap);

	public User addUser(UserRequest request, User user);

	public List<UserRequest> getAllUser();

	public UserRequest buildResponse(UserRoleMap userRoleMap);

	public boolean deleteUser(Long userUid);

	public boolean isPasswordValid(PasswordRequest request);

	public boolean isOtpValid(PasswordRequest request);

	public void updatePassword(PasswordRequest request);

	public OtpResponse generateCode(String userName);

	User findUserByUID(Long userUid) throws Exception;

}

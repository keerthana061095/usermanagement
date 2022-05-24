package com.demo.usersec.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.usersec.model.KeyValue;
import com.demo.usersec.model.Role;
import com.demo.usersec.model.User;
import com.demo.usersec.model.UserRoleMap;
import com.demo.usersec.repository.KeyValueRepository;
import com.demo.usersec.repository.RoleRepository;
import com.demo.usersec.repository.UserRepository;
import com.demo.usersec.repository.UserRoleRepository;
import com.demo.usersec.service.UserService;
import com.demo.usersec.service.request.OtpResponse;
import com.demo.usersec.service.request.PasswordRequest;
import com.demo.usersec.service.request.UserRequest;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${EXP_SECS}")
	private int EXP_SECS;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Autowired
	KeyValueRepository keyValueRepo;

	@Override
	public User addUser(UserRequest data, User user) {

		if (user.getPassword() == null) {
			user.setPassword(bcryptEncoder.encode(data.getPassword()));
		}

		if (data.getFullName() != null) {
			user.setFullName(data.getFullName());
		}
		if (data.getUserName() != null) {
			user.setUserName(data.getUserName().toLowerCase());
		}
		if (data.getEmail() != null) {
			user.setEmail(data.getEmail());
		}

		if (data.getPasscodeVerify() != null) {
			user.setPasscodeVerify(data.getPasscodeVerify());
		}

		user = userRepo.save(user);
		logger.debug("User %s added ", user.getUserName());
		return user;

	}

	@Override
	public UserRoleMap mapUserRole(UserRequest request, User user, UserRoleMap userRoleMap) {
		logger.debug("user " + user + " : " + request.getActive());
		logger.debug("Role id " + request.getRoleUid());
		Optional<Role> role = roleRepo.findById(request.getRoleUid());

		userRoleMap.setUser(user);
		userRoleMap.setRole(role.get());
		userRoleMap.setActive(request.getActive());

		userRoleMap = userRoleRepo.save(userRoleMap);
		logger.debug("UserRoleMap saved");

		// userRoleMap.setCreatedBy(applicationVariables.getLoggedInUser());
		return userRoleMap;
	}

	@Override
	public List<UserRequest> getAllUser() {

		List<UserRoleMap> userRoleMapList = userRoleRepo.findAll();
		List<UserRequest> userList = new ArrayList<UserRequest>();

		for (UserRoleMap user : userRoleMapList) {
			UserRequest userRequest = new UserRequest();
			userRequest.setUserUid(user.getUser().getUserUid());
			userRequest.setFullName(user.getUser().getFullName());
			userRequest.setUserName(user.getUser().getUserName());
			userRequest.setEmail(user.getUser().getEmail());
			userRequest.setPasscodeVerify(user.getUser().getPasscodeVerify());
			userRequest.setActive(user.getActive());
			userRequest.setRoleUid(user.getRole().getRoleUID());
			userRequest.setRoleName(user.getRole().getRoleName());
			userRequest.setPassword(null);

			userList.add(userRequest);

		}
		return userList;
	}

	@Override
	public UserRequest buildResponse(UserRoleMap userRoleMap) {
		UserRequest response = new UserRequest();

		response.setUserUid(userRoleMap.getUser().getUserUid());
		response.setFullName(userRoleMap.getUser().getFullName());
		response.setUserName(userRoleMap.getUser().getUserName());
		response.setEmail(userRoleMap.getUser().getEmail());
		response.setPasscodeVerify(userRoleMap.getUser().getPasscodeVerify());
		response.setRoleUid(userRoleMap.getRole().getRoleUID());
		response.setRoleName(userRoleMap.getRole().getRoleName());
		response.setActive(userRoleMap.getActive());

		return response;
	}

	@Override
	public boolean deleteUser(Long userUid) {
		Optional<User> user = userRepo.findById(userUid);
		if (!user.isPresent()) {
			logger.error("%s user not found for delete ", userUid);
			return false;
		}
		userRepo.deleteByUserUid(userUid);

		logger.info("User %s deleted ", userUid);
		return true;

	}

	@Override
	public boolean isPasswordValid(PasswordRequest request) {
		User user = userRepo.findUserByName(request.getUserName());
		// logger.debug("password " + request.getCurrentPassword() );
		if (bcryptEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public void updatePassword(PasswordRequest request) {
		userRepo.updatePassword(request.getUserName(), bcryptEncoder.encode(request.getNewPassword()));

	}

	@Override
	public boolean isOtpValid(PasswordRequest request) {

		String username = request.getUserName();
		User user = userRepo.findUserByName(username);
		logger.debug("is otp valid " + username);
		if (user == null) {
			logger.error("User invalid " + username);
			return false;
		}
		KeyValue keyValue = keyValueRepo.findValueByKey(username);
		// logger.error("is otp valid");
		if (keyValue == null) {
			logger.error("No OTP found in db for " + username);
			return false;
		}

		String otp = (String) keyValue.getValue().get("otp");

		Long expTs = (Long) keyValue.getValue().get("exp");

		if (hasExpired(new Timestamp(expTs))) {
			logger.error("OTP has expired " + expTs);
			return false;
		}
		if (!otp.trim().equals(request.getOTP().trim())) {
			logger.debug("OTP mismatch: " + otp.trim() + " , " + request.getOTP().trim());
			logger.error("Incorrect OTP ");
			return false;
		}

		logger.debug("OTP %s is valid %t", otp, expTs);
		return true;
	}

	private Timestamp generateExpTime() {
		Long tm = new Date().getTime();
		tm += EXP_SECS;
		Timestamp ts = new Timestamp(tm);
		logger.debug("Curr timestamp " + ts + "\n");
		return ts;
	}

	private boolean hasExpired(Timestamp expTs) {
		Long tm = new Date().getTime();
		Timestamp currTs = new Timestamp(tm);
		logger.debug("curr ts ; exp ts  " + currTs + " , " + expTs);
		if (currTs.before(expTs)) {
			return false;
		}
		return true;
	}

	@Override
	public OtpResponse generateCode(String userName) {
		OtpResponse response = new OtpResponse();
		Map<String, Object> jsonBody = new HashMap<>();
		User user = userRepo.findUserByName(userName);
		if (user != null) {
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(user.getUserName());
			String generatedString = RandomStringUtils.randomAlphanumeric(7);
			jsonBody.put("otp", (String) generatedString);
			Timestamp expTime = generateExpTime();
			jsonBody.put("exp", expTime);
			keyValue.setValue(jsonBody);

			keyValue = keyValueRepo.saveAndFlush(keyValue);
			response.setUsername(userName);
			response.setOtp(generatedString);

			return response;
		} else {
			logger.error("User not found " + userName);
		}
		return response;
	}

	@Override
	public User findUserByUID(Long userUid) throws Exception {
		Optional<User> userOptional = userRepo.findById(userUid);
		if (!userOptional.isPresent())
			throw new Exception("BAD_REQUEST, \"User not found\"");
		return userOptional.get();
	}

}

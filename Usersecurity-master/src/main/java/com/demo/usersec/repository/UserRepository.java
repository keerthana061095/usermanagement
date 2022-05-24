package com.demo.usersec.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.usersec.model.User;

import io.swagger.v3.oas.annotations.Parameter;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "select * from UserList c where c.UserName =:userName", nativeQuery = true)
	public User findUserByName(@Param("userName") String userName);
	
	@Query(value = "select * from UserList where UserUid = :userUid " , nativeQuery = true)
	public User findByUserUid(@Param("userUid") String userUid);
	
	@Modifying
	@Query(value = "delete from UserList where UserUid = :userUid", nativeQuery = true)
	public void deleteByUserUid(@Param("userUid") Long userUid);
	
	@Modifying
	@Query(value = "update UserList set password = :password where username = :userName ", nativeQuery = true)
	public void updatePassword(@Param("userName") String userName, @Param("password") String password);
	
	@Query(value = "select CAST(userName as varchar) userName from UserList c where c.UserUID =:UserUID ", nativeQuery = true)
	public String getUserNameByUID(@Param("UserUID") Long userUID);
	
	public User findByUserNameIgnoreCase(String username);

}

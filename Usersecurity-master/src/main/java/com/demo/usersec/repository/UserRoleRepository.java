package com.demo.usersec.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import com.demo.usersec.model.UserRoleMap;



public interface UserRoleRepository extends JpaRepository<UserRoleMap, Long> {
	@Query(value = "select * from UserRoleMap where UserUID = :userUid ", nativeQuery = true)
	public UserRoleMap findAllByUserUid(@Param ("userUid") Long userUid);

	@Query(value = "select * from UserRoleMap where UserUID = :userUid ", nativeQuery = true)
	public UserRoleMap findByUserUid(@Param ("userUid") Long userUid);
	
}

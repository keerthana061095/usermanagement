package com.demo.usersec.repository;

//import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.usersec.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {
	
	@Query(value = "select * from Role r where r.RoleName = :roleName", nativeQuery = true)
	public Role findAllByName(@Param ("roleName") String roleName);

	public Role save(Role role); 

}

package com.demo.usersec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.usersec.model.KeyValue;

@Repository
public interface KeyValueRepository extends JpaRepository<KeyValue, Long> {

	@Query(value = "select * from KeyValue where key = :key ", nativeQuery = true)
	public KeyValue findValueByKey(@Param("key") String key);

	KeyValue findTopByKey(String key);
}

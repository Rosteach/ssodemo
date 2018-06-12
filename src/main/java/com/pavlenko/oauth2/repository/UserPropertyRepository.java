package com.pavlenko.oauth2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pavlenko.oauth2.entity.UserProperty;

public interface UserPropertyRepository extends JpaRepository<UserProperty, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM user_property p WHERE p.user_id=?1")
	List<UserProperty> findByUserId(Long userId);

	@Query(nativeQuery = true, value = "SELECT * FROM user_property p WHERE p.user_id=?1 AND p.id=?2")
	Optional<UserProperty> findByUserIdAndId(Long userId, Long propId);
}

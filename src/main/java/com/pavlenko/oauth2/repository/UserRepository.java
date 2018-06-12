package com.pavlenko.oauth2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pavlenko.oauth2.entity.User;
import com.pavlenko.oauth2.entity.projection.UserProjection;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT new com.pavlenko.oauth2.entity.projection.UserProjection(u) FROM User u")
	List<UserProjection> findAllProjections();

	@Query("SELECT new com.pavlenko.oauth2.entity.projection.UserProjection(u) FROM User u WHERE u.id=?1")
	Optional<UserProjection> findById(Long id);

}

package com.pavlenko.oauth2.service;

import java.util.List;
import java.util.Optional;

import com.pavlenko.oauth2.entity.projection.UserProjection;
import com.pavlenko.oauth2.entity.projection.UserPropertyProjection;

public interface UserRepoService {

	List<UserProjection> findAll();

	Optional<UserProjection> findById(Long id);

	UserProjection save(UserProjection userProjection);

	Optional<UserProjection> update(Long id, UserProjection userProjection);

	List<UserPropertyProjection> findAllPropsByUserId(Long userId);

	Optional<UserPropertyProjection> findPropByUserIdAndId(Long userId, Long propId);

	Optional<UserPropertyProjection> addNewProp(Long userId, UserPropertyProjection uProjection);

	Optional<UserPropertyProjection> updateProp(Long userId, Long propId, UserPropertyProjection uProjection);

	boolean deleteProp(Long userId, Long propId);
}

package com.pavlenko.oauth2.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pavlenko.oauth2.entity.User;
import com.pavlenko.oauth2.entity.UserProperty;
import com.pavlenko.oauth2.entity.UserRole;
import com.pavlenko.oauth2.entity.projection.UserProjection;
import com.pavlenko.oauth2.entity.projection.UserPropertyProjection;
import com.pavlenko.oauth2.repository.UserPropertyRepository;
import com.pavlenko.oauth2.repository.UserRepository;
import com.pavlenko.oauth2.service.UserRepoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRepoServiceImpl implements UserRepoService {
	private final UserRepository userRepo;
	private final UserPropertyRepository propRepo;
	private final PasswordEncoder passEncoder;

	/**
	 * Causes selection of all users
	 */
	@Override
	public List<UserProjection> findAll() {
		return userRepo.findAllProjections();
	}

	/**
	 * Causes selection of user by id
	 */
	@Override
	public Optional<UserProjection> findById(Long id) {
		return userRepo.findById(id);
	}

	/**
	 * Causes storing of new user
	 */
	@Override
	public UserProjection save(UserProjection uProjection) {
		final User user = userRepo
				.save(new User(uProjection.getUsername(), passEncoder.encode(uProjection.getPassword()), true,
						uProjection.getRoles().stream().map(UserRole::new).collect(Collectors.toList())));
		uProjection.setId(user.getId());
		return uProjection;
	}

	/**
	 * Causes user update operation, return Optional to handle user not found
	 * event
	 */
	@Override
	public Optional<UserProjection> update(Long id, UserProjection uProjection) {
		final User user = userRepo.findOne(id);
		if (user == null)
			return Optional.empty();

		user.setUsername(uProjection.getUsername());
		user.setActive(uProjection.isActive());
		final User updated = userRepo.save(user);
		uProjection.setId(updated.getId());
		return Optional.of(uProjection);
	}

	/**
	 * Causes selection of all user properties
	 */
	@Override
	public List<UserPropertyProjection> findAllPropsByUserId(Long userId) {
		return propRepo.findByUserId(userId).stream().map(UserPropertyProjection::new).collect(Collectors.toList());
	}

	/**
	 * Causes selection of specific property by user id and property id
	 */
	@Override
	public Optional<UserPropertyProjection> findPropByUserIdAndId(Long userId, Long propId) {
		final Optional<UserProperty> optProp = propRepo.findByUserIdAndId(userId, propId);
		return optProp.isPresent() ? Optional.of(new UserPropertyProjection(optProp.get())) : Optional.empty();
	}

	/**
	 * Causes storing of new property
	 */
	@Transactional
	@Override
	public Optional<UserPropertyProjection> addNewProp(Long userId, UserPropertyProjection uProjection) {
		final User user = userRepo.findOne(userId);
		if (user != null) {
			final UserProperty prop = new UserProperty(uProjection);
			prop.setUser(user);
			user.add(prop);
			final UserProperty newProp = propRepo.save(prop);
			uProjection.setId(newProp.getId());
			return Optional.of(uProjection);
		}
		return Optional.empty();
	}

	/**
	 * Causes user property update operation, return Optional to cover not found
	 * event
	 */
	@Override
	public Optional<UserPropertyProjection> updateProp(Long userId, Long propId, UserPropertyProjection uProjection) {
		final Optional<UserProperty> optProp = propRepo.findByUserIdAndId(userId, propId);
		if (optProp.isPresent()) {
			final UserProperty uProp = optProp.get();
			uProp.setName(uProjection.getName());
			uProp.setValue(uProjection.getValue());
			final UserProperty updatedProp = propRepo.save(uProp);
			uProjection.setId(updatedProp.getId());
			return Optional.of(uProjection);
		}
		return Optional.empty();
	}

	/**
	 * Causes user property delete operation
	 */
	@Transactional
	@Override
	public boolean deleteProp(Long userId, Long propId) {
		final User user = userRepo.getOne(userId);
		if (user != null) {
			final List<UserProperty> props = user.getProperties();
			if (props.stream().anyMatch(p -> p.getId().equals(propId))) {
				props.removeIf(p -> p.getId().equals(propId));
				userRepo.save(user);
				return true;
			}
		}
		return false;
	}
}

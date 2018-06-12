package com.pavlenko.oauth2.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pavlenko.oauth2.entity.projection.UserPropertyProjection;
import com.pavlenko.oauth2.service.UserRepoService;

@RestController
@RequestMapping("/api/users")
public class ApiUserPropertyController {
	private final UserRepoService userRepo;

	public ApiUserPropertyController(UserRepoService userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping("/{userId}/properties")
	private ResponseEntity<List<UserPropertyProjection>> findAll(@PathVariable("userId") final Long userId) {
		return ResponseEntity.ok(userRepo.findAllPropsByUserId(userId));
	}

	@GetMapping("/{userId}/properties/{propId}")
	private ResponseEntity<Object> findOne(@PathVariable("userId") final Long userId,
			@PathVariable("propId") final Long propId) {
		final Optional<UserPropertyProjection> optProp = userRepo.findPropByUserIdAndId(userId, propId);
		return optProp.isPresent() ? ResponseEntity.ok(optProp.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping("/{userId}/properties")
	private ResponseEntity<UserPropertyProjection> addOne(@PathVariable("userId") final Long userId,
			@RequestBody final UserPropertyProjection uProjection) {
		final Optional<UserPropertyProjection> optProp = userRepo.addNewProp(userId, uProjection);
		return optProp.isPresent() ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(optProp.get().getId()).toUri()).build()
				: ResponseEntity.notFound().build();
	}

	@PutMapping("/{userId}/properties/{propId}")
	private ResponseEntity<UserPropertyProjection> updateOne(@PathVariable("userId") final Long userId,
			@PathVariable("propId") final Long propId, @RequestBody final UserPropertyProjection uProjection) {
		final Optional<UserPropertyProjection> optProp = userRepo.updateProp(userId, propId, uProjection);
		return optProp.isPresent() ? ResponseEntity.ok(optProp.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{userId}/properties/{propId}")
	private ResponseEntity<Object> deleteOne(@PathVariable("userId") final Long userId,
			@PathVariable("propId") final Long propId) {
		return userRepo.deleteProp(userId, propId) ? ResponseEntity.noContent().build	()
				: ResponseEntity.notFound().build();
	}
}

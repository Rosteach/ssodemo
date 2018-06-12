package com.pavlenko.oauth2.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavlenko.oauth2.entity.projection.UserProjection;
import com.pavlenko.oauth2.service.UserRepoService;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	private final UserRepoService userRepo;

	public ApiUserController(UserRepoService userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping
	private ResponseEntity<List<UserProjection>> findAll() {
		return ResponseEntity.ok(userRepo.findAll());
	}

	@GetMapping("/{id}")
	private ResponseEntity<UserProjection> findOne(@PathVariable("id") Long id) {
		final Optional<UserProjection> user = userRepo.findById(id);
		return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	private ResponseEntity<UserProjection> addOne(@RequestBody UserProjection userProjection) {
		return ResponseEntity.ok(userRepo.save(userProjection));
	}

	@PutMapping("/{id}")
	private ResponseEntity<UserProjection> updateOne(@PathVariable Long id,
			@RequestBody UserProjection userProjection) {
		final Optional<UserProjection> user = userRepo.update(id, userProjection);
		return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
	}

}

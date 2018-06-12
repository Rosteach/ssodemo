package com.pavlenko.oauth2.controller.mav;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.pavlenko.oauth2.entity.projection.UserProjection;
import com.pavlenko.oauth2.service.UserRepoService;

@Component
public class ModelAndViewProvider {
	private final UserRepoService userRepo;

	public ModelAndViewProvider(UserRepoService userRepo) {
		this.userRepo = userRepo;
	}

	public ModelAndView createUsersPage() {
		final ModelAndView mav = new ModelAndView(ViewProps.USERS.value());
		mav.addObject(ViewProps.USERS.value(), userRepo.findAll());
		return mav;
	}

	public ModelAndView createUserPropsPage(Long id) {
		final ModelAndView mav = new ModelAndView(ViewProps.USER_PROPERTIES.value());
		final Optional<UserProjection> optUser = userRepo.findById(id);
		if (optUser.isPresent()) {
			mav.addObject(ViewProps.USERS.value(), optUser.get());
			mav.addObject(ViewProps.USER_PROPERTIES.value(), userRepo.findAllPropsByUserId(optUser.get().getId()));
			return mav;
		}
		return error(HttpStatus.NOT_FOUND, "Invalid user id: " + id);
	}

	private ModelAndView error(HttpStatus status, String message) {
		final ModelAndView mav = new ModelAndView();
		mav.setStatus(status);
		mav.addObject(ViewProps.MESSAGE.value(), message);
		return mav;
	}

}

package com.harsh.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.learningportalnew.dto.FavouriteCourseDTO;
import com.harsh.learningportalnew.dto.RegisteredCourseDTO;
import com.harsh.learningportalnew.dto.UserDTO;
import com.harsh.learningportalnew.entity.CourseEntity;
import com.harsh.learningportalnew.entity.CourseEntity.Category;
import com.harsh.learningportalnew.entity.FavouriteCourseEntity;
import com.harsh.learningportalnew.entity.LogInEntity;
import com.harsh.learningportalnew.entity.UserEntity;
import com.harsh.learningportalnew.entity.UserEntity.Role;
import com.harsh.learningportalnew.mapper.UserMapper;
import com.harsh.learningportalnew.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	//Register User
	@PostMapping("/register")
	public UserDTO registerUser(@RequestBody UserEntity user) {
		//hashing the password
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		log.info("user Registered:{}", user);
		UserDTO resUserDTO = userMapper.toDto(user);

		//registering the user
		return userService.registerUser(resUserDTO);

	}

	//Login User
	@GetMapping("/login/{id}/{userId}")
	public Optional<UserEntity> loginUser(@PathVariable Long id, @PathVariable Long userId) {
		//finding the user
		Optional<UserEntity> isUser = userService.getUser(userId);

		//if user exists
		if (isUser.isPresent()) {
			log.info("user loggedIn");
			return userService.loginUser(id);
		}
		return Optional.empty();
	}

	//validating user
	@GetMapping("/login_")
	public String login(@RequestBody LogInEntity log) {
		//finding the user
		UserEntity user = userService.findByusername(log.getUsername());

		//if user exists
		if (user != null && BCrypt.checkpw(log.getPassword(), user.getPassword())) {
			return "Successfully loggedIn";
		}

		return "Null";
	}

	//Get all users
	@GetMapping("/getall")
	public List<UserEntity> getAllUser() {

		log.info("showing all users");
		return userService.getAllUsers();
	}

	//Deleting users
	@DeleteMapping("/delete/{deleteid}/{adminId}")
	public String deleteUser(@PathVariable Long deleteid, @PathVariable Long adminId) {
		//finding if the users exist
		Optional<UserEntity> isAdmin = userService.getUser(adminId);

		//checking if the user exists and is admin
		if (isAdmin.isPresent() && (isAdmin.get().getRole() == Role.ADMIN)) {
			userService.deleteUser(deleteid);
			log.info("user deleted");
			return "User deleted";
		}

		return "INVALID DELETE_ID and/or ADMIN_ID";
	}

	//Get all courses by caterory
	@GetMapping("/categories/{category}")
	public List<CourseEntity> getByCategory(@PathVariable Category category) {
		//listing courses by category
		log.info("Listing all the courses by category:{} ", category);
		return userService.getCoursesByCategory(category);
	}

	//Purchase course
	@PostMapping("/purchase/{userId}/{courseId}")
	public RegisteredCourseDTO purchaseCourse(@PathVariable Long userId, @PathVariable Long courseId) {

		Optional<UserEntity> isLearner = userService.getUser(userId);

		if (isLearner.isPresent() && (isLearner.get().getRole() == Role.LEARNER)) {

			log.info("course purchased:{}", courseId);
			return userService.purchaseCourse(courseId, userId);
		}
		return null;

	}

	//Adding a favorite course
	@PostMapping("/favourite/{registrationId}")
	public FavouriteCourseDTO addFavouriteCourse(@PathVariable Long registrationId) {

		log.info("course added to favourites");
		return userService.favouriteCourse(registrationId);
	}

	//See favorite course
	@GetMapping("/favourite/seeAll/{userId}")
	public List<FavouriteCourseEntity> seeAllFavourite(@PathVariable Long userId) {

		log.info("listing all the favourite courses");
		return userService.seeFavouriteCourses(userId);
	}

	@GetMapping("/RegisteredCourses")
	public List<Object[]> seeUsersWithRegisteredCourses() {

		log.info("listing all the users with registered courses");
		return userService.getUsersWithRegisteredCourses();
	}

}

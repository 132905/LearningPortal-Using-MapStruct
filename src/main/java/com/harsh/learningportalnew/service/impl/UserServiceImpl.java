package com.harsh.learningportalnew.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.learningportalnew.dto.FavouriteCourseDTO;
import com.harsh.learningportalnew.dto.RegisteredCourseDTO;
import com.harsh.learningportalnew.dto.UserDTO;
import com.harsh.learningportalnew.entity.CourseEntity;
import com.harsh.learningportalnew.entity.CourseEntity.Category;
import com.harsh.learningportalnew.entity.FavouriteCourseEntity;
import com.harsh.learningportalnew.entity.RegisteredCourseEntity;
import com.harsh.learningportalnew.entity.UserEntity;
import com.harsh.learningportalnew.mapper.FavouriteMapper;
import com.harsh.learningportalnew.mapper.RegisteredMapper;
import com.harsh.learningportalnew.mapper.UserMapper;
import com.harsh.learningportalnew.repository.CourseRepository;
import com.harsh.learningportalnew.repository.FavouriteCourseRepository;
import com.harsh.learningportalnew.repository.RegisteredCourseRepository;
import com.harsh.learningportalnew.repository.UserRepository;
import com.harsh.learningportalnew.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private RegisteredCourseRepository registeredCourseRepository;
	@Autowired
	private FavouriteCourseRepository favouriteCourseRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RegisteredMapper registeredMapper;

	@Autowired
	private FavouriteMapper favouriteMapper;

	//get all users
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();

	}

	@Override
	public Optional<UserEntity> getUser(Long id) {
		return userRepository.findById(id);
	}

	//deleting an user
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}

	//adding and user by a admin
	@Override
	public UserDTO addUser(UserDTO user) {
		UserEntity userEntity = userMapper.toEntity(user);
		userEntity.setRegistrationDateTime(LocalDateTime.now());
		UserEntity saveUserEntity = userRepository.save(userEntity);
		return userMapper.toDto(saveUserEntity);
	}

	//get courses by category
	@Override
	public List<CourseEntity> getCoursesByCategory(Category category) {
		return courseRepository.findByCategory(category);
	}

	//logging in an user
	@Override
	public Optional<UserEntity> loginUser(Long userId) {
		return userRepository.findById(userId);
	}

	//registering an user
	@Override
	public UserDTO registerUser(UserDTO user) {

		UserEntity userEntity = userMapper.toEntity(user);
		userEntity.setRegistrationDateTime(LocalDateTime.now());
		UserEntity saveUserEntity = userRepository.save(userEntity);
		return userMapper.toDto(saveUserEntity);
	}

	//purchasing a course 
	@Override
	@Transactional
	public RegisteredCourseDTO purchaseCourse(Long courseId, Long userId) {

		//finding if the course and user exist
		Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
		Optional<UserEntity> optionalUser = userRepository.findById(userId);

		//if course and user exist
		if (!optionalCourse.isEmpty() && !optionalUser.isEmpty()) {
			CourseEntity course = optionalCourse.get();
			UserEntity user = optionalUser.get();

			//set course and user reference in registered course
			RegisteredCourseEntity registeredCourse = new RegisteredCourseEntity();
			registeredCourse.setCourse(course);
			registeredCourse.setUser(user);

			//saving the registered course
			RegisteredCourseEntity regCourse = registeredCourseRepository.save(registeredCourse);

			return registeredMapper.toDto(regCourse);
		}
		return new RegisteredCourseDTO();
	}

	//adding a course to favourite
	@Override
	public FavouriteCourseDTO favouriteCourse(Long registrationId) {
		// finding if the registered course exist
		Optional<RegisteredCourseEntity> regCourse = registeredCourseRepository.findById(registrationId);

		//if it exist the find it in favourites and return it
		if (regCourse.isPresent()) {
			RegisteredCourseEntity reigisteredCourse = regCourse.get();
			FavouriteCourseEntity favouriteCourse = new FavouriteCourseEntity();
			favouriteCourse.setRegisteredCourse(reigisteredCourse);

			FavouriteCourseEntity favCourse = favouriteCourseRepository.save(favouriteCourse);
			return favouriteMapper.toDto(favCourse);
		}
		return new FavouriteCourseDTO();

	}

	//listing all your favourite courses
	@Override
	public List<FavouriteCourseEntity> seeFavouriteCourses(Long userId) {
		//finding all registered courses
		List<RegisteredCourseEntity> registeredCourses = registeredCourseRepository.findByUserId(userId);
		//List to store favourite courses for a specific user
		List<FavouriteCourseEntity> favouriteCourses = new ArrayList<>();

		// Extract IDs of registered courses
		List<Long> registeredCourseIds = registeredCourses.stream().map(RegisteredCourseEntity::getRegistrationId)
				.collect(Collectors.toList());

		// Find favorite courses for the registered courses
		for (Long id : registeredCourseIds) {
			List<FavouriteCourseEntity> favouriteCoursesForRegistrationId = favouriteCourseRepository
					.findByRegistrationId(id);
			favouriteCourses.addAll(favouriteCoursesForRegistrationId);
		}

		return favouriteCourses;

	}

	@Override
	public UserEntity findByusername(String username) {
		return userRepository.findByusername(username);
		//		return Optional.empty();
	}

}

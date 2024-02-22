package com.harsh.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.learningportalnew.dto.CourseDTO;
import com.harsh.learningportalnew.entity.CourseEntity;
import com.harsh.learningportalnew.entity.UserEntity;
import com.harsh.learningportalnew.entity.UserEntity.Role;
import com.harsh.learningportalnew.service.CourseService;
import com.harsh.learningportalnew.service.UserService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	private final UserService userService;
	private static final Logger log = LoggerFactory.getLogger(CourseController.class);

	public CourseController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}

	//ADD COURSES
	@PostMapping("/add/{userid}")
	public CourseDTO addCourse(@RequestBody CourseDTO course, @PathVariable Long userid) {
		//finding the author from the passed user_id
		Optional<UserEntity> author = userService.getUser(userid);

		//if user is present and the role is author
		if (author.isPresent() && (author.get().getRole() == Role.AUTHOR)) {
			log.info("course added");
			//add course
			return courseService.addCourse(course);
		}
		//return empty course
		return new CourseDTO();
	}

	//GET COURSES
	@GetMapping("/getall")
	public List<CourseEntity> getAllCourses() {
		log.info("listing all courses");
		return courseService.getAllCourses();
	}

	//UPDATE COURSES
	@PutMapping("/update/{user_Id}")
	public CourseDTO updateCourse(@RequestBody CourseDTO course, @PathVariable Long user_Id) {
		//finding if the user is present
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			log.info("course updated");
			return courseService.updateCourse(course);
		}
		return new CourseDTO();
	}

	//DELETE COURSES
	@DeleteMapping("/delete/{user_Id}/{courseId}")
	public void deleteCourse(@PathVariable Long courseId, @PathVariable Long user_Id) {
		//finding if the user is present 
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if the user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			//delete the course
			courseService.deleteCourse(courseId);
			log.info("course deleted");
		}
	}

}
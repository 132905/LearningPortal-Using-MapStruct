package com.harsh.learningportalnew.service;

import java.util.List;

import com.harsh.learningportalnew.dto.CourseDTO;
import com.harsh.learningportalnew.entity.CourseEntity;

public interface CourseService {

	//AUTHOR
	//get all courses
	List<CourseEntity> getAllCourses();

	//add courses
	CourseDTO addCourse(CourseDTO course);

	//delete courses
	void deleteCourse(Long courseId);

	//update course
	CourseDTO updateCourse(CourseDTO course);

}

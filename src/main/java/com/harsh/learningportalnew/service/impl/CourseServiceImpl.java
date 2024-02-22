package com.harsh.learningportalnew.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.learningportalnew.dto.CourseDTO;
import com.harsh.learningportalnew.entity.CourseEntity;
import com.harsh.learningportalnew.mapper.CourseMapper;
import com.harsh.learningportalnew.repository.CourseRepository;
import com.harsh.learningportalnew.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CourseMapper courseMapper;

	@Override
	public List<CourseEntity> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public CourseDTO addCourse(CourseDTO course) {

		CourseEntity courseEntity = courseMapper.toEntity(course);
		courseEntity.setRegistrationDateTime(LocalDateTime.now());
		CourseEntity saveCourse = courseRepository.save(courseEntity);

		return courseMapper.toDto(saveCourse);
	}

	@Override
	public void deleteCourse(Long courseId) {
		courseRepository.deleteById(courseId);
	}

	@Override
	public CourseDTO updateCourse(CourseDTO course) {
		//checking if the course exists
		Optional<CourseEntity> existingCourse = courseRepository.findById(course.getCourseId());

		//if course exists
		if (existingCourse.isPresent()) {

			CourseEntity presentCourse = courseMapper.toEntity(course);

			//saving the course
			CourseEntity changedCourse = courseRepository.save(presentCourse);

			return courseMapper.toDto(changedCourse);

		}
		//returning empty course
		return new CourseDTO();
	}

}

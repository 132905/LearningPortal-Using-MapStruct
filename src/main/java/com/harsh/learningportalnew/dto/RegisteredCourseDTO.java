package com.harsh.learningportalnew.dto;

import com.harsh.learningportalnew.entity.CourseEntity;
import com.harsh.learningportalnew.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredCourseDTO {

	private Long registrationId;
	private UserEntity user;
	private CourseEntity course;

}

package com.harsh.learningportalnew.dto;

import com.harsh.learningportalnew.entity.RegisteredCourseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteCourseDTO {
	private Long favouriteId;
	private RegisteredCourseEntity registeredCourse;

}

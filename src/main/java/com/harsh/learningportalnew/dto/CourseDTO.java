package com.harsh.learningportalnew.dto;

import com.harsh.learningportalnew.entity.CourseEntity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

	private Long courseId;
	private String title;
	private String description;
	private Long price;
	private Category category;
}

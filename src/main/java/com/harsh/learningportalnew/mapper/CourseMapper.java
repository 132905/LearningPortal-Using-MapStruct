package com.harsh.learningportalnew.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.harsh.learningportalnew.dto.CourseDTO;
import com.harsh.learningportalnew.entity.CourseEntity;

@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, CourseEntity> {
	CourseDTO toDto(CourseEntity entity);

	CourseEntity toEntity(CourseDTO dto);

	List<CourseDTO> toDto(List<CourseEntity> elist);

	List<CourseEntity> toEntity(List<CourseDTO> dlist);

}

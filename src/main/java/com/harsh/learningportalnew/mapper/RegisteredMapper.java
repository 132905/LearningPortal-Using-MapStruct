package com.harsh.learningportalnew.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.harsh.learningportalnew.dto.RegisteredCourseDTO;
import com.harsh.learningportalnew.entity.RegisteredCourseEntity;

@Mapper(componentModel = "spring")
public interface RegisteredMapper extends EntityMapper<RegisteredCourseDTO, RegisteredCourseEntity> {
	RegisteredCourseDTO toDto(RegisteredCourseEntity entity);

	RegisteredCourseEntity toEntity(RegisteredCourseDTO dto);

	List<RegisteredCourseDTO> toDto(List<RegisteredCourseEntity> elist);

	List<RegisteredCourseEntity> toEntity(List<RegisteredCourseDTO> dlist);

}
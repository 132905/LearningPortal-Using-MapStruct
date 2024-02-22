package com.harsh.learningportalnew.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.harsh.learningportalnew.dto.FavouriteCourseDTO;
import com.harsh.learningportalnew.entity.FavouriteCourseEntity;

@Mapper(componentModel = "spring")
public interface FavouriteMapper extends EntityMapper<FavouriteCourseDTO, FavouriteCourseEntity> {
	FavouriteCourseDTO toDto(FavouriteCourseEntity entity);

	FavouriteCourseEntity toEntity(FavouriteCourseDTO dto);

	List<FavouriteCourseDTO> toDto(List<FavouriteCourseEntity> elist);

	List<FavouriteCourseEntity> toEntity(List<FavouriteCourseDTO> dlist);

}

package com.harsh.learningportalnew.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.harsh.learningportalnew.dto.UserDTO;
import com.harsh.learningportalnew.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, UserEntity> {
	UserDTO toDto(UserEntity entity);

	UserEntity toEntity(UserDTO dto);

	List<UserDTO> toDto(List<UserEntity> elist);

	List<UserEntity> toEntity(List<UserDTO> dlist);

}

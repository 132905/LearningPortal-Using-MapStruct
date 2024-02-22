package com.harsh.learningportalnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.learningportalnew.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByusername(String name);
}

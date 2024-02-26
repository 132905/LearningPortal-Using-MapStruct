package com.harsh.learningportalnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.learningportalnew.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByusername(String name);

	@Query(value = "SELECT u.username AS username, c.title AS title " + "FROM users u "
			+ "INNER JOIN registered_courses r ON u.user_Id = r.user_Id "
			+ "INNER JOIN course c ON r.course_id = c.course_id", nativeQuery = true)
	List<Object[]> getUsersWithRegisteredCoursesQuery();

	// @Query(value = "SELECT u.username AS username, c.title AS title " + "FROM users u "
	// 		+ "INNER JOIN favourite_Course f ON u.user_Id = f.user_Id "
	// 		+ "INNER JOIN course c ON f.course_id = c.course_id", nativeQuery = true)
	// List<Object[]> getUsersWithFavouriteCoursesQuery();
}

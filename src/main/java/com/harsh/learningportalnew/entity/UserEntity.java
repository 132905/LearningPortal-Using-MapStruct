package com.harsh.learningportalnew.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	public enum Role {
		ADMIN, AUTHOR, LEARNER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_Id")
	private Long userId;

	//	@JsonFormat(pattern = "MM/dd/yyyy HH:mm")
	//	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@CreatedDate
	private LocalDateTime registrationDateTime;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "user_Role")
	@Enumerated(EnumType.STRING)
	private Role role;

	//adding one to many relation
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<RegisteredCourseEntity> registeredCourses = new HashSet<>();

	public void addRegisteredCourse(RegisteredCourseEntity registeredCourse) {
		registeredCourses.add(registeredCourse);
		registeredCourse.setUser(this);
	}

}

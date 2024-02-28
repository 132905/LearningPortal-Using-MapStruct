package com.harsh.learningportalnew;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.harsh.learningportalnew.dto.UserDTO;
import com.harsh.learningportalnew.entity.UserEntity;
import com.harsh.learningportalnew.entity.UserEntity.Role;
import com.harsh.learningportalnew.mapper.UserMapper;
import com.harsh.learningportalnew.repository.UserRepository;
import com.harsh.learningportalnew.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class LearningPortalNewApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@MockBean
	private UserRepository userRepository;

	@Test
	void testGetAllUsers() {
		when(userRepository.findAll()).thenReturn(Stream.of(new UserDTO("123", "Sam", Role.ADMIN))
				.map(userMapper::toEntity).collect(Collectors.toList()));

		assertEquals(1, userService.getAllUsers().size());
	}

	@Test
	void testRegister() {
		UserDTO userDTO = new UserDTO("!@#", "manya", Role.ADMIN);
		when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
			UserEntity savedEntity = invocation.getArgument(0);

			return savedEntity;
		});

		assertEquals(userDTO, userService.registerUser(userDTO));

	
	}

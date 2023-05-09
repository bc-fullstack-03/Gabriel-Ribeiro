package com.sysmap.parrot;

import com.sysmap.parrot.controller.UserController;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ParrotApplicationTests {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;
	@Test
	void contextLoads() {
	}
	@Test
	public void testFetchAllUsers() {
		List<User> userList = new ArrayList<>();
		User user1 = new User("user1", "user1@example.com", "password");
		User user2 = new User("user2", "user2@example.com", "password");
		user1.setFollowing(new ArrayList<>());
		user1.setFollowers(new ArrayList<>());
		user1.setAvatar("");
		user1.setDescription("");
		user2.setFollowing(new ArrayList<>());
		user2.setFollowers(new ArrayList<>());
		user2.setAvatar("");
		user2.setDescription("");
		userList.add(user1);
		userList.add(user2);
		System.out.println("asdasdasdasd");
		System.out.println(userList);
		Mockito.when(userService.getAllUsers()).thenReturn(userList);

		ResponseEntity<List<User>> response = userController.fetchAllUsers();

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(userList, response.getBody());
	}
}

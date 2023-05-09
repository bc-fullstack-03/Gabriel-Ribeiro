package com.sysmap.parrot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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

    @Test
    public void testFetchUserByUsername() {
        User user = new User("user1", "user1@example.com", "password");
        Optional<User> optionalUser = Optional.of(user);

        Mockito.when(userService.getUserByUsername("user1")).thenReturn(optionalUser);

        ResponseEntity<Optional<User>> response = userController.fetchUserByUsername("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(optionalUser, response.getBody());
    }

    @Test
    public void testFetchUserByUsernameNotFound() {
        Optional<User> optionalUser = Optional.empty();

        Mockito.when(userService.getUserByUsername("user1")).thenReturn(optionalUser);

        ResponseEntity<Optional<User>> response = userController.fetchUserByUsername("user1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(optionalUser, response.getBody());
    }

    @Test
    public void testFollowUser() {
        String id = "user1";
        String userId = "user2";

        Mockito.when(userService.followUser(id)).thenReturn("Now following user2");

        ResponseEntity<String> response = userController.followUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Now following user2", response.getBody());
    }

    @Test
    public void testEditUser() {
        String id = "user1";
        CreateUserRequest request = new CreateUserRequest();
                          request.setEmail("user1@example.com");
                          request.setPassword("newpassword");

        User user = new User("user1", "user1@example.com", "newpassword");

        Mockito.when(userService.editUser(id, request)).thenReturn(user);

        ResponseEntity<User> response = userController.editUser(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testDeleteUser() throws ChangeSetPersister.NotFoundException {
        String id = "user1";

        Mockito.doNothing().when(userService).deleteUser(id);

        ResponseEntity<String> response = userController.deleteUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usu\u00e1rio com ID user1 foi deletado.", response.getBody());
    }

    @Test
    public void testDeleteUserNotFound() throws ChangeSetPersister.NotFoundException {
        String id = "user1";

        Mockito.doThrow(new ChangeSetPersister.NotFoundException()).when(userService).deleteUser(id);

        ResponseEntity<String> response = userController.deleteUser(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usu\u00e1rio com esse ID n\u00e3o foi encontrado!", response.getBody());
    }

    // Test uploadAvatar with valid file
    @Test
    public void testUploadAvatarWithValidFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes()
        );

        ResponseEntity<String> response = userController.uploadAvatar(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avatar do usu\u00e1rio foi atualizado.", response.getBody());
    }

    // Test uploadAvatar with invalid file type
    @Test
    public void testUploadAvatarWithInvalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        ResponseEntity<String> response = userController.uploadAvatar(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Tipo de arquivo inv\u00e1lido. O arquivo deve ser uma imagem.", response.getBody());
    }

    // Test uploadAvatar with empty file
    @Test
    public void testUploadAvatarWithEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "",
                MediaType.IMAGE_JPEG_VALUE,
                "".getBytes()
        );

        ResponseEntity<String> response = userController.uploadAvatar(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O arquivo n\u00e3o pode estar vazio.", response.getBody());
    }
}

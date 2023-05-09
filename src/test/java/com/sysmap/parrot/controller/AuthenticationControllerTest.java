package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.AuthenticateResponse;
import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.dto.CreateRegisterUserRequest;
import com.sysmap.parrot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void testLoginUser() {
        CreateLoginRequest request = new CreateLoginRequest();

        request.setEmail("testuser@mail.com");
        request.setPassword("testpassword");

        AuthenticateResponse response = new AuthenticateResponse();
        when(userService.login(request)).thenReturn(response);
        ResponseEntity<AuthenticateResponse> result = authenticationController.loginUser(request);
        verify(userService).login(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testCreateUser() {
        CreateRegisterUserRequest request = new CreateRegisterUserRequest();
        request.setUsername("testuser");
        request.setEmail("testemail@mail.com");
        request.setPassword("testpassword");

        when(userService.createUser(request)).thenReturn("User created successfully");

        ResponseEntity<String> result = authenticationController.createUser(request);
        System.out.println(result);
        verify(userService).createUser(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User created successfully", result.getBody());
    }


}

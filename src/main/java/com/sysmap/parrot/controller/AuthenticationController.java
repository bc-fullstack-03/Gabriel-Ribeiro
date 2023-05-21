package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.dto.CreateRegisterUserRequest;
import com.sysmap.parrot.services.UserService;
import com.sysmap.parrot.dto.AuthenticateResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/authentication")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> loginUser(@Valid @RequestBody CreateLoginRequest request){
        var response = userService.login(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateRegisterUserRequest request){
        var response = userService.createUser(request);
        return ResponseEntity.status(200).body(response);
    }

}

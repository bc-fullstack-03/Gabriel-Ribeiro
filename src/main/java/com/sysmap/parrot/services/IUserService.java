package com.sysmap.parrot.services;

import com.sysmap.parrot.dto.AuthenticateResponse;
import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.dto.CreateRegisterUserRequest;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.entities.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(String userId);

    String createUser(CreateRegisterUserRequest request);

    AuthenticateResponse login(CreateLoginRequest request);

    User editUser(String id, CreateUserRequest request);

    String followUser(String id);

    void deleteUser(String id) throws ChangeSetPersister.NotFoundException;

    String uploadAvatar(MultipartFile photo) throws Exception;
}

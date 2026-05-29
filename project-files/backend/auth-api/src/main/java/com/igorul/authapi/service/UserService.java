package com.igorul.authapi.service;

import com.igorul.authapi.dto.CreateUserResponse;
import com.igorul.authapi.model.User;
import com.igorul.authapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponse createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already in use");
        };

        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already in use");
        };

        user.setPass_hash(passwordEncoder.encode(user.getPass_hash()));

        userRepository.save(user);

        CreateUserResponse response = new CreateUserResponse(user.getId(), user.getUsername(), user.getEmail(),
                "Password set succesefully");

        return response;
    }

    public List<CreateUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> {
                    CreateUserResponse dto = new CreateUserResponse();

                    dto.userId = user.getId();
                    dto.username = user.getUsername();
                    dto.email = user.getEmail();
                    dto.passwordStatus = "Secret";

                    return dto;
                })
                .toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    public CreateUserResponse updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        CreateUserResponse response = new CreateUserResponse(user.getId(), user.getUsername(), user.getEmail(), "Password has been maintained");

        User existingUser =
                userRepository.findByUsername(updatedUser.getUsername());

        if (updatedUser.getUsername() != null) {

            if (existingUser != null &&
                    !existingUser.getId().equals(user.getId())) {

                throw new RuntimeException("Username already in use");
            }

            user.setUsername(updatedUser.getUsername());
            response.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null) {

            if (existingUser != null &&
                    !existingUser.getId().equals(user.getId())) {

                throw new RuntimeException("Email already in use");
            }

            user.setEmail(updatedUser.getEmail());
            response.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPass_hash() != null) {
            user.setPass_hash(passwordEncoder.encode(updatedUser.getPass_hash()));
            response.setPasswordStatus("Password updated succesefully");
        }

        userRepository.save(user);

        return response;
    }
}
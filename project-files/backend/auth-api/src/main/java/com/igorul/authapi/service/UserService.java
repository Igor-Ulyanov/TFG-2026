package com.igorul.authapi.service;

import com.igorul.authapi.model.User;
import com.igorul.authapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already in use");
        };

        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already in use");
        };

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (updatedUser.getUsername() != null) {

            User existingUser =
                    userRepository.findByUsername(updatedUser.getUsername());

            if (existingUser != null &&
                    !existingUser.getId().equals(user.getId())) {

                throw new RuntimeException("Username already in use");
            }

            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null) {

            User existingUser =
                    userRepository.findByEmail(updatedUser.getEmail());

            if (existingUser != null &&
                    !existingUser.getId().equals(user.getId())) {

                throw new RuntimeException("Email already in use");
            }

            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPass_hash() != null) {
            user.setPass_hash(updatedUser.getPass_hash());
        }


        return userRepository.save(user);
    }
}
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
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow();

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPass_hash() != null) {
            user.setPass_hash(updatedUser.getPass_hash());
        }


        return userRepository.save(user);
    }
}
package com.example.Abacus.Service;

import org.springframework.stereotype.Service;

import com.example.Abacus.Model.User;
import com.example.Abacus.Repo.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try {
            if (user.getName() != null) {
                return userRepository.save(user);
            } else {
                throw new RuntimeException("User name cannot be null");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while creating user: " + e.getMessage(), e);
        }
    }


    public User getUserById(int id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Exception while fetching user: " + e.getMessage(), e);
        }
    }
}

package dev.zac.lockbox.service;

import dev.zac.lockbox.entity.User;
import dev.zac.lockbox.repository.impl.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
        userRepository.update(user);
        return user;
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    public User getUserById(String id) throws Exception {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

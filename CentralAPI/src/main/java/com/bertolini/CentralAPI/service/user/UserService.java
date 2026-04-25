package com.bertolini.CentralAPI.service.user;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.repository.UserRepository;
import com.bertolini.CentralAPI.schema.error.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null)
            throw new UserNotFoundException("User with id: " + userId + " not found.");
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id: " + id + " not found. Can't be deleted.");
        }
        userRepository.deleteById(id);
    }

    public int resetAllUserRequests() {
        return userRepository.resetAllUserRequests();
    }

}

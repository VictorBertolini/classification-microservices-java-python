package com.bertolini.CentralAPI.service.user;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.repository.UserRepository;
import com.bertolini.CentralAPI.schema.error.UserNotFoundException;
import com.bertolini.CentralAPI.schema.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public User findByUserEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserNotFoundException("User with email: " + email + " not found.");
        return user;
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public int resetAllUserRequests() {
        return userRepository.resetAllUserRequests();
    }

    public void update(User user, UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.username() != null) {
            user.setUsername(userUpdateRequest.username());
        }
        if (userUpdateRequest.password() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.password()));
        }
    }
}

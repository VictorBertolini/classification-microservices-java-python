package com.bertolini.CentralAPI.domain;

import com.bertolini.CentralAPI.schema.user.UserRequest;
import com.bertolini.CentralAPI.schema.user.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "requests_remain")
    private int requestsRemain;

    public User(UserRequest userRequest) {
        this.username = userRequest.username();
        this.email = userRequest.email();
        this.password = userRequest.password();
        this.role = UserRole.FREE;
        this.requestsRemain = UserRole.FREE.getRequestLimit();
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.username() != null) {
            this.username = userUpdateRequest.username();
        }
        if (userUpdateRequest.password() != null) {
            this.password = userUpdateRequest.password();
        }
    }
}

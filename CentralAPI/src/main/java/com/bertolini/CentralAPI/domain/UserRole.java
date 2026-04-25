package com.bertolini.CentralAPI.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(10000),
    PREMIUM(1000),
    FREE(100);
    
    UserRole(int requestLimit) {
        this.requestLimit = requestLimit;
    }

    private final int requestLimit;
}

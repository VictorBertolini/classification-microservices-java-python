package com.bertolini.CentralAPI.service.requestServices;

import com.bertolini.CentralAPI.service.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RequestResetService {

    private final UserService userService;

    public RequestResetService(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserRequests() {
        userService.resetAllUserRequests();
    }
}

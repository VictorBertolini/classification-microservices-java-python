package com.bertolini.CentralAPI.controller;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.schema.text.TextClassifiedResponse;
import com.bertolini.CentralAPI.schema.text.TextClassifyRequest;
import com.bertolini.CentralAPI.service.classification.ClassificationService;
import com.bertolini.CentralAPI.service.requestServices.RequestValidationService;
import com.bertolini.CentralAPI.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/{userId}/classify")
public class ClassifierController {

    private final ClassificationService classificationService;
    private final UserService userService;
    private final RequestValidationService requestValidationService;

    public ClassifierController(ClassificationService classificationService, UserService userService, RequestValidationService requestValidationService) {
        this.classificationService = classificationService;
        this.userService = userService;
        this.requestValidationService = requestValidationService;
    }

    @PostMapping
    public ResponseEntity<TextClassifiedResponse> classify(@PathVariable Long userId, @RequestBody TextClassifyRequest textClassifyRequest) {
        User user = userService.findByUserId(userId);
        requestValidationService.execute(textClassifyRequest);
        TextClassifiedResponse response = classificationService.classify(user, textClassifyRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TextClassifiedResponse>> classify(@PathVariable Long userId, @RequestBody List<TextClassifyRequest> requests) {
        User user = userService.findByUserId(userId);
        requestValidationService.execute(requests);
        List<TextClassifiedResponse> response = classificationService.classifyBatch(user, requests);
        return ResponseEntity.ok(response);
    }
}

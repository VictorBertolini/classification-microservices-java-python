package com.bertolini.CentralAPI.controller;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.schema.text.TextClassifiedResponse;
import com.bertolini.CentralAPI.schema.text.TextClassifyRequest;
import com.bertolini.CentralAPI.service.classification.ClassificationService;
import com.bertolini.CentralAPI.service.requestServices.RequestValidationService;
import com.bertolini.CentralAPI.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classify")
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
    @Transactional
    public ResponseEntity<TextClassifiedResponse> classify(@AuthenticationPrincipal User loggedUser, @RequestBody TextClassifyRequest textClassifyRequest) {
        requestValidationService.execute(textClassifyRequest);
        TextClassifiedResponse response = classificationService.classify(loggedUser, textClassifyRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    @Transactional
    public ResponseEntity<List<TextClassifiedResponse>> classify(@AuthenticationPrincipal User loggedUser, @RequestBody List<TextClassifyRequest> requests) {
        requestValidationService.execute(requests);
        List<TextClassifiedResponse> response = classificationService.classifyBatch(loggedUser, requests);
        return ResponseEntity.ok(response);
    }
}

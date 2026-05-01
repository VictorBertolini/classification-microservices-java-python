package com.bertolini.CentralAPI.service.classification;

import com.bertolini.CentralAPI.domain.TextClassify;
import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.domain.UserRole;
import com.bertolini.CentralAPI.repository.TextClassifyRepository;
import com.bertolini.CentralAPI.repository.UserRepository;
import com.bertolini.CentralAPI.schema.error.InsufficientRequestsException;
import com.bertolini.CentralAPI.schema.text.TextClassifiedResponse;
import com.bertolini.CentralAPI.schema.text.TextClassifyRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassificationService {

    private final ClassifyApiClient classifyApiClient;
    private final TextClassifyRepository repository;
    private final UserRepository userRepository;

    public ClassificationService(ClassifyApiClient classifyApiClient, TextClassifyRepository repository, UserRepository userRepository) {
        this.classifyApiClient = classifyApiClient;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TextClassifiedResponse classify(User user, TextClassifyRequest textClassifyRequest) {
        validateAndConsumeRequests(user, 1);
        TextClassifiedResponse response = this.classifyApiClient.send(textClassifyRequest);
        this.repository.save(new TextClassify(user, response));
        return response;
    }

    @Transactional
    public List<TextClassifiedResponse> classifyBatch(User user, List<TextClassifyRequest> requests) {
        validateAndConsumeRequests(user, requests.size());
        List<TextClassifiedResponse> response = this.classifyApiClient.send(requests);
        this.repository.saveAll(response.stream().map(r -> new TextClassify(user, r)).toList());
        return response;
    }

    private void validateAndConsumeRequests(User user, int requestSize) {
        if (user.getRole() == UserRole.ADMIN)
            return;

        if (user.getRequestsRemain() < requestSize)
            throw new InsufficientRequestsException("User " + user.getEmail() + " has " + user.getRequestsRemain() + " insufficient to finished the request");

        user.setRequestsRemain(user.getRequestsRemain() - requestSize);
        userRepository.save(user);
    }
}

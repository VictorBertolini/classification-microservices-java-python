package com.bertolini.CentralAPI.service.classification;

import com.bertolini.CentralAPI.domain.TextClassify;
import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.repository.TextClassifyRepository;
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

    public ClassificationService(ClassifyApiClient classifyApiClient, TextClassifyRepository repository) {
        this.classifyApiClient = classifyApiClient;
        this.repository = repository;
    }

    @Transactional
    public TextClassifiedResponse classify(User user, TextClassifyRequest textClassifyRequest) {
        TextClassifiedResponse response;
        if (user.getRequestsRemain() <= 0) {
            throw new InsufficientRequestsException("User " + user.getEmail() + " has " + user.getRequestsRemain() + " insufficient to finished the request");
        }

        response = this.classifyApiClient.send(textClassifyRequest);
        user.setRequestsRemain(user.getRequestsRemain() - 1);
        this.repository.save(new TextClassify(user, response));

        return response;
    }

    @Transactional
    public List<TextClassifiedResponse> classifyBatch(User user, List<TextClassifyRequest> requests) {
        List<TextClassifiedResponse> response;
        if (user.getRequestsRemain() < requests.size()) {
            throw new InsufficientRequestsException("User " + user.getEmail() + " has " + user.getRequestsRemain() + " insufficient to finished the request");
        }

        response = this.classifyApiClient.send(requests);
        user.setRequestsRemain(user.getRequestsRemain() - requests.size());
        this.repository.saveAll(response.stream().map(r -> new TextClassify(user, r)).toList());

        return response;
    }


}

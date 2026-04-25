package com.bertolini.CentralAPI.service.classification;

import com.bertolini.CentralAPI.schema.text.TextClassifiedResponse;
import com.bertolini.CentralAPI.schema.text.TextClassifyRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Getter
@Component
public class ClassifyApiClient {

    private final RestClient restClient;

    public ClassifyApiClient(@Value("${api.client.base-url}") String baseUrl,
                             @Value("${external.api.key}")    String apiKey) {
        this.restClient = RestClient.builder()
                            .baseUrl(baseUrl)
                            .defaultHeader("api_key", apiKey)
                            .build();
    }

    public TextClassifiedResponse send(TextClassifyRequest textClassifyRequest) {
        return restClient
                .post()
                .uri("/classify")
                .body(textClassifyRequest)
                .retrieve()
                .body(TextClassifiedResponse.class);
    }

    public List<TextClassifiedResponse> send(List<TextClassifyRequest> requests) {
        return restClient
                .post()
                .uri("/classify/batch")
                .body(requests)
                .retrieve()
                .body(new ParameterizedTypeReference<List<TextClassifiedResponse>>() {});
    }
}

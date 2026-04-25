package com.bertolini.CentralAPI.service.requestServices;

import com.bertolini.CentralAPI.schema.error.TextOutOfBorderException;
import com.bertolini.CentralAPI.schema.text.TextClassifyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestValidationService {

    private final int textLimit = 1000;

    public void execute(TextClassifyRequest textRequest) {
        int len = textRequest.text().length();

        if (len > textLimit) {
            throw new TextOutOfBorderException(textLimit);
        }
    }

    public void execute(List<TextClassifyRequest> requests) {
        for(TextClassifyRequest req : requests) {
            execute(req);
        }
    }
}

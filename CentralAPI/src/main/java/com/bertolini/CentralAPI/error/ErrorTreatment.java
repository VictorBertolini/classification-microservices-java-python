package com.bertolini.CentralAPI.error;

import com.bertolini.CentralAPI.schema.error.ErrorResponse;
import com.bertolini.CentralAPI.schema.error.InsufficientRequestsException;
import com.bertolini.CentralAPI.schema.error.TextOutOfBorderException;
import com.bertolini.CentralAPI.schema.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class ErrorTreatment {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> treatInvalidArguments(MethodArgumentNotValidException error) {
        FieldError fieldError = error.getFieldError();
        ErrorResponse response = new ErrorResponse(
                400,
                "MethodArgumentNotValidException - " + fieldError.getField(),
                "Invalid Request Arguments: " + fieldError.getDefaultMessage()
                );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> treatInvalidJson() {
        ErrorResponse response = new ErrorResponse(
                400,
                "HttpMessageNotReadableException",
                "Invalid JSON Arguments"
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> treatInvalidUser(UserNotFoundException e) {
        int status = 404;
        ErrorResponse response = new ErrorResponse(
                status,
                "UserNotFoundException",
                e.getMessage()
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(InsufficientRequestsException.class)
    public ResponseEntity<ErrorResponse> treatInsufficientRequests(InsufficientRequestsException e) {
        int status = 402;
        ErrorResponse response = new ErrorResponse(
                status,
                "InsufficientRequestsException",
                e.getMessage()
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> treatExternalApiDown() {
        int status = 502;
        ErrorResponse response = new ErrorResponse(
                status,
                "ResourceAccessException",
                "Service Unavailable"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> treatExternalApiError() {
        int status = 502;
        ErrorResponse response = new ErrorResponse(
                status,
                "HttpServerErrorException",
                "Service with an Error"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(TextOutOfBorderException.class)
    public ResponseEntity<ErrorResponse> treatTextOutOfLimitCharacter(TextOutOfBorderException e) {
        int status = 406;
        ErrorResponse response = new ErrorResponse(
                status,
                "Text out of border",
                e.getMessage()
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> treatGenericClassificationServiceExceptions() {
        int status = 503;
        ErrorResponse response = new ErrorResponse(
                status,
                "Service Unavailable",
                "Classification service is temporarily unavailable"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> treatBadCredentialsError() {
        int status = 401;
        ErrorResponse response = new ErrorResponse(
                status,
                "Bad Credentials",
                "Check your email and password"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> treatAuthenticationError() {
        int status = 401;
        ErrorResponse response = new ErrorResponse(
                status,
                "Authentication Error",
                "User can't be Authenticated"
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> treatAccessDenied() {
        int status = 403;
        ErrorResponse response = new ErrorResponse(
                status,
                "Access Denied",
                "Access can't be made"
        );
        return ResponseEntity.status(status).body(response);
    }
}

package com.revature.util;

import com.revature.dtos.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseFactory {

    public ErrorResponse generateErrorResponse(int status, String message) {
        return new ErrorResponse(status, message, System.currentTimeMillis());
    }

    public ErrorResponse generateErrorResponse(HttpStatus status) {
        return new ErrorResponse(status.value(), status.toString(), System.currentTimeMillis());
    }

}
package com.revature.util;

import com.revature.dtos.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseFactory {

    /**
     * Generates an {@code ErrorResponse} object holding the provided
     * HTTP status code and message
     * @param status
     * @param message
     * @return an {@code ErrorResponse} object
     */
    public ErrorResponse generateErrorResponse(int status, String message) {
        return new ErrorResponse(status, message, System.currentTimeMillis());
    }

    /**
     * Generates an {@code ErrorResponse} object holding only the provided
     * HTTP status code
     * @param status
     * @return an {@code ErrorResponse} object
     */
    public ErrorResponse generateErrorResponse(HttpStatus status) {
        return new ErrorResponse(status.value(), status.toString(), System.currentTimeMillis());
    }

}
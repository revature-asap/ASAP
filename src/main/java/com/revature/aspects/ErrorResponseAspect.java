package com.revature.aspects;

import com.revature.dtos.ErrorResponse;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.util.ErrorResponseFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ErrorResponseAspect {

    private ErrorResponseFactory errRespFactory;

    @Autowired
    public ErrorResponseAspect(ErrorResponseFactory errRespFactory) {
        this.errRespFactory = errRespFactory;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidRequestException(InvalidRequestException e) {
        return errRespFactory.generateErrorResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return errRespFactory.generateErrorResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleResourcePersistenceException(ResourcePersistenceException e) {
        return errRespFactory.generateErrorResponse(HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException e) {
        return errRespFactory.generateErrorResponse(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthorizationException(AuthorizationException e) {
        return errRespFactory.generateErrorResponse(HttpStatus.FORBIDDEN);
    }

}
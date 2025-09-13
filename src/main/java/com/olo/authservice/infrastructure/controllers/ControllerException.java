package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.domain.exception.DomainException;
import com.olo.authservice.domain.exception.token.InvalidTokenException;
import com.olo.authservice.domain.exception.token.MissingTokenException;
import com.olo.authservice.domain.exception.token.TokenRevokedException;
import com.olo.authservice.domain.exception.user.*;
import com.olo.authservice.utils.HttpResponseUtil;
import com.olo.internalauthlibrary.exceptions.permissions.InvalidPermissionValueException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerException {

    private final HttpResponseUtil httpResponseUtil;

    public ControllerException(HttpResponseUtil httpResponseUtil) {
        this.httpResponseUtil = httpResponseUtil;
    }

    @ExceptionHandler(DomainException.class)
    public void handleDomainException(DomainException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status;

        if (ex instanceof InvalidTokenException || ex instanceof TokenRevokedException || ex instanceof InvalidCredentialsException || ex instanceof SuperAdminCreationDeniedException || ex instanceof AccountLockedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof MissingTokenException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UsernameTakenException || ex instanceof EmailRegisteredException) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        httpResponseUtil.writeErrorResponse(response, status, ex.getClass().getSimpleName(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public void handleOtherExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        httpResponseUtil.writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", ex.getMessage(), request.getRequestURI());
    }
}

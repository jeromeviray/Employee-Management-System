package com.project.execption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomGlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalExceptions( Exception exception, WebRequest request) {
        ApiError apiError = new ApiError( new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception, WebRequest request){
        ApiError apiError = new ApiError( new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException exception, WebRequest request){
        ApiError apiError = new ApiError( new Date(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = AuthenticatingCredentialsException.class)
    public ResponseEntity<?> handleAuthenticatingCredentialsException(AuthenticatingCredentialsException exception, WebRequest request){
        ApiError apiError = new ApiError( new Date(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception, WebRequest request){
        ApiError apiError = new ApiError( new Date(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request ) {

        ApiError apiError = new ApiError(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                "Invalid Value",
                request.getDescription( false ));

        apiError.addValidationErrors( ex.getBindingResult().getFieldErrors() );
        apiError.addValidationError( ex.getBindingResult().getGlobalErrors() );
        return new ResponseEntity<>( apiError, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<?> handleInvalidException(InvalidException invalidException,
                                                    WebRequest request) {
        ApiError apiError = new ApiError(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                invalidException.getMessage(),
                request.getDescription(true));
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

}

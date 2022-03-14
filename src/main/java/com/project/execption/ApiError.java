package com.project.execption;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiError {
    @JsonFormat( pattern = "hh:mm:ss dd-MM-yyyy", shape = JsonFormat.Shape.STRING )
    private Date timestamp;
    private int status;
    private HttpStatus error;
    private String message;
    private String details;
    private List<SubApiError> subErrors;

    public ApiError() {
    }

    public ApiError( Date timestamp, String message, String details ) {
        this();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ApiError( HttpStatus status, String message, String error) {
        this();
        this.message = message;
        this.timestamp = new Date();
        this.status = status.value();
    }

    public ApiError( Date timestamp, int status, HttpStatus error, String message) {
        this();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ApiError( Date timestamp, int status, HttpStatus error, String message, String details) {
        this();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }
    private void addSubError(SubApiError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }


    private void addValidationError(String object, String message) {
        addSubError(new SubApiError(object, message));
    }
    private void addValidationError( ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }
    private void addValidationError(String object, String field,  String message,  Object rejectedValue) {
        addSubError(new SubApiError(object, field, rejectedValue, message));
    }
    private void addValidationError( FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError( HttpStatus error ) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails( String details ) {
        this.details = details;
    }

    public List<SubApiError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors( List<SubApiError> subErrors ) {
        this.subErrors = subErrors;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error=" + error +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", subErrors=" + subErrors +
                '}';
    }
}

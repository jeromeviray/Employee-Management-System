package com.project.execption;

public class AuthenticatingCredentialsException extends RuntimeException{
    public AuthenticatingCredentialsException( String message ) {
        super( message );
    }
}

package com.project.execption;


public class JwtException extends RuntimeException{
    public JwtException( String message ) {
        super( message );
    }
}

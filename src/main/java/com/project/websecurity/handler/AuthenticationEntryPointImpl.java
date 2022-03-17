package com.project.websecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.execption.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    Logger logger = LoggerFactory.getLogger( AuthenticationEntryPointImpl.class );
    private static final long serialVersionUID = 1L;

    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException e ) throws IOException, ServletException {
        logger.info( "Error occur in Entry Point {}", e.getMessage() );
        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
        response.setContentType( APPLICATION_JSON_VALUE );

        ApiError error = new ApiError( new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED,
                "Invalid Credentials",
                request.getServletPath() );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write( mapper.writeValueAsString( error ) );
    }
}

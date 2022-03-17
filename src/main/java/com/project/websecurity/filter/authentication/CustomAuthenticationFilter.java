package com.project.websecurity.filter.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.account.model.Account;
import com.project.execption.AuthenticatingCredentialsException;
import com.project.websecurity.jwtUtil.provider.JwtProvider;
import com.project.websecurity.jwtUtil.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.project.common.contants.AppConstant.ENDPOINT_VERSION_ONE;
import static com.project.common.contants.AppConstant.REQUEST_TOKEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final static Logger logger = LoggerFactory.getLogger( CustomAuthenticationFilter.class );
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter( AuthenticationManager authenticationManager, JwtProvider jwtProvider ) {
        super( authenticationManager );
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl( ENDPOINT_VERSION_ONE.concat( REQUEST_TOKEN ) );
    }

    public CustomAuthenticationFilter() {
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            authenticationToken = getRequestParameters( request );
            return authenticationManager.authenticate( authenticationToken );
        } catch( IOException e ) {
            throw new AuthenticatingCredentialsException("Something went wrong: " + e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) throws IOException, ServletException {
        User user = ( User ) authentication.getPrincipal();
        response.setContentType( APPLICATION_JSON_VALUE );
        // tokens
        String token = jwtProvider.doGenerateAccessToken( user.getUsername() );

        JwtResponse jwtResponse = new JwtResponse( user.getUsername(), token );
        new ObjectMapper().writeValue( response.getOutputStream(), jwtResponse );
    }

    private UsernamePasswordAuthenticationToken getRequestParameters( HttpServletRequest request ) throws IOException {
        Account account = new ObjectMapper().readValue( request.getInputStream(), Account.class );
        String loginInfo = String.format( "Date: %s username account %s", new Date(), account.getUsername() );
        logger.info( "Login logs: {} ", loginInfo );
        return new UsernamePasswordAuthenticationToken( account.getUsername(), account.getPassword() );
    }
}

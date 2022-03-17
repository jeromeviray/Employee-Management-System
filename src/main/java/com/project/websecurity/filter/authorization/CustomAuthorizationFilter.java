package com.project.websecurity.filter.authorization;

import com.project.execption.JwtException;
import com.project.websecurity.jwtUtil.provider.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.project.common.contants.AppConstant.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger( CustomAuthorizationFilter.class );

    private JwtProvider jwtProvider;

    private UserDetailsService userDetailsService;

    public CustomAuthorizationFilter( JwtProvider jwtProvider, UserDetailsService userDetailsService ) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        if( request.getServletPath().equals( ENDPOINT_VERSION_ONE.concat( REQUEST_TOKEN ) )) {
            filterChain.doFilter( request, response );
        }else{
            String token = resolveToken( request );
            String username = null;
            if( token != null ) {
                try {
                    username = jwtProvider.getUserNameFromToken( token );
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = userDetailsService.loadUserByUsername( username );

                        if( jwtProvider.validateToken( token, userDetails.getUsername() ) ){
                            Authentication authentication = getAuthentication(userDetails);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                    filterChain.doFilter( request, response );
                } catch( ExpiredJwtException exception ) {
                    logger.info("JWT Token has expired");
                    throw new JwtException( exception.getMessage() );

                }catch (IllegalArgumentException e) {
                    logger.info("Unable to get JWT token");
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                filterChain.doFilter( request, response );
            }
        }
    }
    private Authentication getAuthentication(UserDetails userDetails){
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities() );
    }

    private String resolveToken( HttpServletRequest req ) {
        String header = req.getHeader( AUTHORIZATION );
        if( header != null && header.startsWith( "Bearer " ) ) {
            return header.substring( 7, header.length() );
        }
        return null;
    }
}

package com.project.websecurity.jwtUtil.provider;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtProvider {
    String getUserNameFromToken( String token );

    Date getExpirationDateFromToken( String token );

    <T> T getClaimFromToken( String token, Function<Claims, T> claimsResolver );

    Claims getAllClaimsFromToken( String token );

    Boolean isTokenExpired( String token );

    String doGenerateAccessToken( String username );

    Boolean validateToken( String token, String username );

}

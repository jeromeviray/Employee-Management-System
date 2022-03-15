package com.project.websecurity.jwtUtil.provider.impl;

import com.project.websecurity.jwtUtil.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.project.common.contants.AppConstant.ACCESS_TOKEN_EXPIRATION_DATE;
import static com.project.common.contants.AppConstant.SECRET_KEY;

@Service
public class JwtProviderImpl implements JwtProvider {

    @Override
    public String getUserNameFromToken( String token ) {
        return getClaimFromToken( token, Claims::getSubject );
    }

    @Override
    public Date getExpirationDateFromToken( String token ) {
        return getClaimFromToken( token, Claims::getExpiration );
    }

    @Override
    public <T> T getClaimFromToken( String token, Function<Claims, T> claimsResolver ) {
        final Claims claims = getAllClaimsFromToken( token );
        return claimsResolver.apply( claims );
    }

    @Override
    public Claims getAllClaimsFromToken( String token ) {
        return Jwts.parser().setSigningKey( SECRET_KEY ).parseClaimsJws( token ).getBody();
    }

    @Override
    public Boolean isTokenExpired( String token ) {
        final Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }


    @Override
    public String doGenerateAccessToken( String username ) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims( claims )
                .setSubject( username )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date(System.currentTimeMillis() + (ACCESS_TOKEN_EXPIRATION_DATE)) )
                .signWith( SignatureAlgorithm.HS512, SECRET_KEY ).compact();
    }

    @Override
    public Boolean validateToken( String token, String username ) {
        final String usernameFromToken = getUserNameFromToken( token );
        return ( usernameFromToken.equals( username ) && !isTokenExpired( token ) );
    }

//    @Override
//    public UserDetails getUserDetails( String username ) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername( username );
//        return userDetails;
//    }
//
//    @Override
//    public List<GrantedAuthority> getAuthorities( Account account ) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
////        for ( Role role : account.getRoles() ) {
////            authorities.add( new SimpleGrantedAuthority( "ROLE_" + role.getRoleName() ) );
////        }
//        authorities.add( new SimpleGrantedAuthority( "ROLE_"+account.getRole().getRoleName() ) );
//
//        return authorities;
//    }
}

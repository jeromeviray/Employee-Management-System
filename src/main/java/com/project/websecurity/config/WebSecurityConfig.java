package com.project.websecurity.config;

import com.project.websecurity.filter.authentication.CustomAuthenticationFilter;
import com.project.websecurity.filter.authorization.CustomAuthorizationFilter;
import com.project.websecurity.jwtUtil.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.project.common.contants.AppConstant.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomAuthorizationFilter customAuthorizationFilter;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers( HttpMethod.POST,
                        ENDPOINT_VERSION_ONE.concat( REQUEST_TOKEN ) )
                .permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        http.exceptionHandling()
                .authenticationEntryPoint( authenticationEntryPoint );
//        http.formLogin().disable();
        http.addFilter( new CustomAuthenticationFilter(authenticationManagerBean(), jwtProvider) );
        http.addFilterBefore(customAuthorizationFilter , UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.authenticationProvider( daoAuthenticationProvider() );
        auth.userDetailsService( userDetailsService );
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder( passwordEncoder );
        provider.setUserDetailsService( userDetailsService );
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

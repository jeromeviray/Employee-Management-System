package com.project.websecurity.config;

import com.project.account.model.ChangePassword;
import com.project.account.repository.AccountRepository;
import com.project.account.repository.RoleRepository;
import com.project.account.service.AccountService;
import com.project.account.service.RoleService;
import com.project.account.service.impl.AccountServiceImpl;
import com.project.account.service.impl.RoleServiceImpl;
import com.project.employee.repository.EmployeeRepository;
import com.project.employee.service.EmployeeService;
import com.project.employee.service.impl.EmployeeServiceImpl;
import com.project.websecurity.filter.authorization.CustomAuthorizationFilter;
import com.project.websecurity.handler.AuthenticationEntryPointImpl;
import com.project.websecurity.impl.UserDetailsServiceImpl;
import com.project.websecurity.jwtUtil.provider.JwtProvider;
import com.project.websecurity.jwtUtil.provider.impl.JwtProviderImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
public class BeanConfig {

//    @Bean
//    public AccountService accountService() {
//        return new AccountServiceImpl();
//    }

    @Bean
    public RoleService roleService() {
        return new RoleServiceImpl();
    }
    @Bean
    public JwtProvider jwtProvider(){
        return new JwtProviderImpl();
    }
    @Bean
    public UserDetailsService userDetailsService(AccountService accountService) {
        return new UserDetailsServiceImpl( accountService );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccountService accountService( AccountRepository accountRepository, ModelMapper modelMapper ) {
        return new AccountServiceImpl( accountRepository, roleService(), passwordEncoder(), modelMapper );
    }

    @Bean
    public CustomAuthorizationFilter customAuthorizationFilter( JwtProvider provider, UserDetailsService userDetailsService ) {
        return new CustomAuthorizationFilter( provider, userDetailsService );
    }

    @Bean
    public EmployeeService employeeService( EmployeeRepository employeeRepository ) {
        return new EmployeeServiceImpl( employeeRepository );
    }

    @Bean
    public RoleService roleService( RoleRepository roleRepository ) {
        return new RoleServiceImpl( roleRepository );
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ChangePassword changePassword(){
        return new ChangePassword();
    }
//    // customAuthenticationFilter
//    @Bean
//    public CustomAuthenticationFilter customAuthenticationFilter(){
//        return new CustomAuthenticationFilter();
//    }
}

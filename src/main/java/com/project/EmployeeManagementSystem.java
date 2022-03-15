package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( value = "com.project" )
public class EmployeeManagementSystem extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run( EmployeeManagementSystem.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure( SpringApplicationBuilder applicationBuilder ) {
		return applicationBuilder.sources( EmployeeManagementSystem.class );
	}
}

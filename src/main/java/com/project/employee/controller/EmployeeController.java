package com.project.employee.controller;

import com.project.employee.model.Employee;
import com.project.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.project.common.contants.AppConstant.*;


@RestController
@RequestMapping( ENDPOINT_VERSION_ONE )
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = EMPLOYEES, method = RequestMethod.POST )
    public ResponseEntity<?> saveEmployee( @Valid @RequestBody Employee employee ) {
        employeeService.saveEmployee( employee );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

    @RequestMapping( value = EMPLOYEES + PATH_VARIABLE_NAME, method = RequestMethod.GET )
    public ResponseEntity<?> findALlEmployeesByName( @PathVariable( name = "name", required = false ) String query,
                                                     @RequestParam( value = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false ) Integer page,
                                                     @RequestParam( value = "limit", defaultValue = DEFAULT_PAGE_SIZE, required = false ) Integer limit,
                                                     @RequestParam( value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false ) String sortBy,
                                                     @RequestParam( value = "orderBy", defaultValue = DEFAULT_ORDER_BY, required = false ) String orderBy ) {

        Map<String, Object> employeeResponse = getEmployees( query, page, limit, sortBy, orderBy );

        return new ResponseEntity<>( employeeResponse, HttpStatus.OK );

    }

    @RequestMapping( value = EMPLOYEES, method = RequestMethod.GET )
    public ResponseEntity<?> findALlEmployees( @RequestParam( value = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false ) Integer page,
                                               @RequestParam( value = "limit", defaultValue = DEFAULT_PAGE_SIZE, required = false ) Integer limit,
                                               @RequestParam( value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false ) String sortBy,
                                               @RequestParam( value = "orderBy", defaultValue = DEFAULT_ORDER_BY, required = false ) String orderBy ) {

        Map<String, Object> employeeResponse = getEmployees( "", page, limit, sortBy, orderBy );

        return new ResponseEntity<>( employeeResponse, HttpStatus.OK );

    }

    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping( value = EMPLOYEES + PATH_VARIABLE_ID, method = RequestMethod.PUT )
    public ResponseEntity<?> updateEmployee( @PathVariable( "id" ) Long id, @Valid @RequestBody Employee employee ) {
        Employee updatedEmployee = employeeService.updateEmployee( id, employee );

        return new ResponseEntity<>( updatedEmployee, HttpStatus.OK );
    }
    @PreAuthorize( "hasRole('ROLE_ADMIN')" )
    @RequestMapping(value = EMPLOYEES+PATH_VARIABLE_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee( id );
        return new ResponseEntity<>(  HttpStatus.OK );
    }
    private Map<String, Object> getEmployees( String query, Integer page, Integer limit, String sortBy, String orderBy ) {
        Sort sort = orderBy.equalsIgnoreCase( Sort.Direction.ASC.name() ) ? Sort.by( sortBy ).ascending() : Sort.by( sortBy ).descending();
        Pageable pageable = PageRequest.of( page, limit, sort );
        Page<Employee> employees = employeeService.getEmployees( query, pageable );

        Map<String, Object> employeeResponse = new HashMap<>();

        employeeResponse.put( "data", employees.getContent() );
        employeeResponse.put( "currentPage", employees.getNumber() );
        employeeResponse.put( "totalPages", employees.getTotalPages() );

        return employeeResponse;
    }

}

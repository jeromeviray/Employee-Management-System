package com.project.employee.service.impl;

import com.project.employee.model.Employee;
import com.project.employee.repository.EmployeeRepository;
import com.project.employee.service.EmployeeService;
import com.project.execption.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl( EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee( Employee employee ) {
        return employeeRepository.save( employee );
    }

    @Override
    public Employee updateEmployee( long id, Employee employee ) {

        Employee getEmployee = getEmployeeById( id );

        getEmployee.setFirstName( employee.getFirstName() );
        getEmployee.setLastName( employee.getLastName() );
        getEmployee.setMiddleName( employee.getMiddleName() );
        getEmployee.setAddress( employee.getAddress() );
        getEmployee.setBirthday( employee.getBirthday() );
        getEmployee.setEmail( employee.getEmail() );

        return saveEmployee(getEmployee);
    }

    @Override
    public void deleteEmployee( long id ) {
        employeeRepository.deleteById( getEmployeeById( id ).getId() );
    }

    @Override
    public Page<Employee> getEmployees( String query, Pageable pageable ) {
        Page<Employee> employees = employeeRepository.findAllEmployeeByName( query, pageable );
        return new PageImpl<>(employees.getContent(), pageable, employees.getTotalElements());
    }

    @Override
    public Employee getEmployeeById( long id ) {
        Optional<Employee> employee = employeeRepository.findById( id );
        if(!employee.isPresent()) throw new NotFoundException( String.format( "Employee not found. Employee Id: %s", id ) );

        return employee.get();

    }
}

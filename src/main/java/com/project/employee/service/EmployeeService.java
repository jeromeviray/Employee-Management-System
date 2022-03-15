package com.project.employee.service;

import com.project.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Employee saveEmployee( Employee employee );

    Employee updateEmployee( long id, Employee employee );

    void deleteEmployee( long id );

    Page<Employee> getEmployees( String query, Pageable pageable );

//    Employee findEmployeeByName(String name);

    Employee getEmployeeById( long id);
}

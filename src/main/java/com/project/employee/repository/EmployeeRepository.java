package com.project.employee.repository;

import com.project.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee_management_system.employees " +
            "WHERE firstname LIKE concat('%', :query, '%') " +
            "OR lastname LIKE concat('%', :query, '%')",
            nativeQuery = true
    )
    Page<Employee> findAllEmployeeByName(@Param("query") String query, Pageable pageable );

    @Override
    Optional<Employee> findById(Long id);
}

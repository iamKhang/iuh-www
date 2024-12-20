package com.example.test03.repository;

import com.example.test03.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByEmployeeCodeContaining(String employeeCode);
    
    @Query("SELECT e FROM Employee e WHERE " +
           "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
           "(:employeeCode IS NULL OR e.employeeCode LIKE %:employeeCode%)")
    List<Employee> searchByDepartmentOrCode(
        @Param("departmentId") Long departmentId, 
        @Param("employeeCode") String employeeCode
    );
} 
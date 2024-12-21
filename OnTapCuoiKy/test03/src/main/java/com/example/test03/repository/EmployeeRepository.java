package com.example.test03.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test03.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByEmployeeCodeContaining(String employeeCode);
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);
    List<Employee> findByEmailContaining(String email);
    
    @Query("SELECT e FROM Employee e WHERE " +
           "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
           "(:employeeCode IS NULL OR e.employeeCode LIKE %:employeeCode%)")
    List<Employee> searchByDepartmentOrCode(
        @Param("departmentId") Long departmentId, 
        @Param("employeeCode") String employeeCode
    );
    
    @Query("SELECT e FROM Employee e WHERE e.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Employee> findByDateOfBirthBetween(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
    
    @Query("SELECT e.department.name, COUNT(e) FROM Employee e GROUP BY e.department.name")
    List<Object[]> countEmployeesByDepartment();
    
    @Query("SELECT e FROM Employee e WHERE YEAR(CURRENT_DATE) - YEAR(e.dateOfBirth) > :age")
    List<Employee> findEmployeesOlderThan(@Param("age") int age);
    
    List<Employee> findByGenderAndDepartmentId(Boolean gender, Long departmentId);
    
    @Query("SELECT e FROM Employee e WHERE " +
           "(:fullName IS NULL OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
           "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
           "(:gender IS NULL OR e.gender = :gender)")
    List<Employee> searchEmployees(
        @Param("fullName") String fullName,
        @Param("departmentId") Long departmentId,
        @Param("gender") Boolean gender
    );
} 
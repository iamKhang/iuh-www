package com.example.test03.service;

import com.example.test03.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee saveEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> searchEmployees(Long departmentId, String employeeCode);
} 
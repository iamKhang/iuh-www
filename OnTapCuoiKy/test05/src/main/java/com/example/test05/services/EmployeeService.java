package com.example.test05.services;

import com.example.test05.entities.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Employee getEmployeeById(long id);

    Employee createEmployee(Employee employee);

    boolean deleteEmployee(long id);
}

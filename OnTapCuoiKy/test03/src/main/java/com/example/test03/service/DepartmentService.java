package com.example.test03.service;

import java.util.List;

import com.example.test03.entity.Department;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department saveDepartment(Department department);
    void deleteDepartment(Long id);
} 
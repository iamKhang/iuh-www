package com.example.test05.services;

import com.example.test05.entities.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentByID(long id);
}

package com.example.test03.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.test03.entity.Employee;
import com.example.test03.repository.EmployeeRepository;
import com.example.test03.service.EmployeeService;
import com.example.test03.util.EmployeeCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            employee.setEmployeeCode(EmployeeCodeGenerator.generateEmployeeCode());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(Long departmentId, String employeeCode) {
        if (departmentId == null && (employeeCode == null || employeeCode.trim().isEmpty())) {
            return getAllEmployees();
        }
        return employeeRepository.searchByDepartmentOrCode(
            departmentId,
            employeeCode != null && !employeeCode.trim().isEmpty() ? employeeCode.trim() : null
        );
    }
} 
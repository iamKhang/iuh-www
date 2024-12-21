package com.example.test05.services.implt;

import com.example.test05.entities.Department;
import com.example.test05.repositories.DepartmentRepository;
import com.example.test05.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImplt implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentByID(long id){
        return departmentRepository.findById(id).orElse(null);
    }
}

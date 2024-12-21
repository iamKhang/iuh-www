package com.example.reviewfinal.services;

import com.example.reviewfinal.entities.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student getById(int id);
    Student addStudent(Student student);
    boolean deleteStudent(int id);
}

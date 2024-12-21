package com.example.reviewfinal.controllers;

import com.example.reviewfinal.entities.Student;
import com.example.reviewfinal.services.ClazzService;
import com.example.reviewfinal.services.StudentService;
import com.example.reviewfinal.utils.GenerateId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class StudentController {
    private final StudentService studentService;
    private final ClazzService clazzService;

    @GetMapping({"/", "/students"})
    public String index(Model model){
        model.addAttribute("students", studentService.findAll());
        return "index";
    }

    @GetMapping("/student/add")
    public String getAdd(Model model){
        model.addAttribute("student", new Student());
        model.addAttribute("classes", clazzService.findAll());
        return "form-add";
    }

    @PostMapping("/student/add")
    public String addStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model){
        if(result.hasErrors()){
            return "form-add";
        }
        student.setCode(GenerateId.generateId());
        studentService.addStudent(student);
        return  "redirect:/";
    }

    @GetMapping("/student/delete/{id}")
    public String deleteStudent(Model model, @PathVariable int id){
        studentService.deleteStudent(id);
        return  "redirect:/";
    }
}

package com.example.test05.controllers;

import com.example.test05.entities.Department;
import com.example.test05.entities.Employee;
import com.example.test05.services.DepartmentService;
import com.example.test05.services.EmployeeService;
import com.example.test05.utils.GeneratecCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping({"/", "/employees"})
    public String index(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployee());
        return "index";
    }

    @GetMapping("/employee/add")
    public String showFormAdd(Model model) {
        Employee employee = new Employee();
        employee.setDepartment(new Department());
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee-add";
    }

    @PostMapping("/employee/add")
    public String showFormAdd(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employee-add";
        }

        Department department = departmentService.getDepartmentByID(employee.getDepartment().getId());
        employee.setDepartment(department);

        employee.setCode(GeneratecCode.getID());

        employeeService.createEmployee(employee);

        return "redirect:/";
    }

}

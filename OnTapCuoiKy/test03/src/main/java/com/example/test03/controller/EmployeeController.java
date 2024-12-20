package com.example.test03.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test03.entity.Department;
import com.example.test03.entity.Employee;
import com.example.test03.service.DepartmentService;
import com.example.test03.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Employee employee = new Employee();
        employee.setDepartment(new Department());
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute Employee employee, 
                             BindingResult result, 
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee/form";
        }
        
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department department = departmentService.getDepartmentById(employee.getDepartment().getId());
            employee.setDepartment(department);
        }
        
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployees(
        @RequestParam(required = false) Long departmentId,
        @RequestParam(required = false) String employeeCode,
        Model model
    ) {
        List<Employee> employees;
        if (departmentId == null && (employeeCode == null || employeeCode.trim().isEmpty())) {
            employees = employeeService.getAllEmployees();
        } else {
            employees = employeeService.searchEmployees(departmentId, employeeCode);
        }
        
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("searchEmployeeCode", employeeCode);
        return "employee/list";
    }
} 
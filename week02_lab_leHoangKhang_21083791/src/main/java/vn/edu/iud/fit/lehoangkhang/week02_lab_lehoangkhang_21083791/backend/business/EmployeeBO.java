package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.business;

import jakarta.inject.Inject;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Employee;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeBO {
    @Inject
    private EmployeeRepository employeeRepository;

    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.deleteEmployee(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();

    }
}

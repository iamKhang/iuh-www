package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Employee;

import java.util.List;

public class EmployeeRepository {

    @PersistenceContext(unitName = "week02_lab_lehoangkhang_21083791")
    private EntityManager entityManager;

    @Transactional
    public void addEmployee(Employee employee) {
        try {
            entityManager.persist(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            entityManager.merge(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee) {
        try {
            entityManager.remove(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAll() {
        return entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }
}

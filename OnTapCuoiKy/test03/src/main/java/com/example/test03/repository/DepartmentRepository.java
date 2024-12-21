package com.example.test03.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test03.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Tìm phòng ban theo tên
    List<Department> findByNameContainingIgnoreCase(String name);
    
    // Tìm các phòng ban có số nhân viên lớn hơn số cho trước
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :count")
    List<Department> findDepartmentsWithMoreEmployeesThan(@Param("count") int count);
    
    // Đếm số nhân viên trong mỗi phòng ban
    @Query("SELECT d.name, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.id, d.name")
    List<Object[]> countEmployeesPerDepartment();
    
    // Tìm phòng ban có nhiều nhân viên nam nhất
    @Query("SELECT d FROM Department d JOIN d.employees e " +
           "WHERE e.gender = true " +
           "GROUP BY d.id " +
           "ORDER BY COUNT(e) DESC")
    List<Department> findDepartmentWithMostMaleEmployees();
    
    // Tìm phòng ban không có nhân viên nào
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) = 0")
    List<Department> findDepartmentsWithNoEmployees();
    
    // Tìm phòng ban có nhân viên trẻ nhất
    @Query("SELECT d FROM Department d JOIN d.employees e " +
           "WHERE e.dateOfBirth = (SELECT MAX(e2.dateOfBirth) FROM Employee e2)")
    List<Department> findDepartmentsWithYoungestEmployee();
} 
package com.example.test05.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name")
    @Size(min = 2, max = 20, message = "Ten phai co tu 2 den 20 ki tu")
    private String fullName;
    @Column(unique = true)
    private String code;
    private String email;
    private LocalDate dob;
    private boolean gender;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

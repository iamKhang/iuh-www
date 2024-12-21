package com.example.reviewfinal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    @Column(name = "full_name")
    private String fullName;
    @Past(message = "Ngay sinh khong duoc sau ngay hien tai")
    private LocalDate dob;
    @Email(message = "Email chua dung dinh dang")
    private String email;
    @Column(name = "number_phone")
    private String numberPhone;
    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;
}

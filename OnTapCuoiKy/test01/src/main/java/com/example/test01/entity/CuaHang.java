package com.example.test01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUAHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuaHang {
    @Id
    @Column(name = "MACUAHANG")
    private String maCuaHang;
    
    @Column(name = "TEN")
    private String ten;
} 
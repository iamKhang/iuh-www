package com.example.test01.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class DonHangDTO {
    private String maDonHang;
    
    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(min = 5, max = 50, message = "Tên khách hàng phải từ 5-50 ký tự")
    private String tenKhachHang;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
    
    @NotNull(message = "Ngày đặt hàng không được để trống")
    @Future(message = "Ngày đặt hàng phải lớn hơn ngày hiện tại")
    private LocalDateTime ngayDatHang;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSp;
    
    // Getters and setters
} 
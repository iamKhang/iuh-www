package com.example.test01.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DONHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHang {
    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "MADONHANG")
    private String maDonHang;
    
    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(min = 5, max = 50, message = "Tên khách hàng phải từ 5-50 ký tự")
    @Column(name = "TENKHACHHANG")
    private String tenKhachHang;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "EMAIL")
    private String email;
    
    @NotNull(message = "Ngày đặt hàng không được để trống")
    @Future(message = "Ngày đặt hàng phải lớn hơn ngày hiện tại")
    @Column(name = "NGAYDATHANG")
    private LocalDateTime ngayDatHang;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "TENSP")
    private String tenSp;
    
    @Column(name = "TRANGTHAI")
    private Integer trangThai = 1;
    
    @ManyToOne
    @JoinColumn(name = "MACUAHANG")
    private CuaHang cuaHang;
    
    public void setCuaHang(String maCuaHang) {
        if (maCuaHang != null && !maCuaHang.isEmpty()) {
            CuaHang ch = new CuaHang();
            ch.setMaCuaHang(maCuaHang);
            this.cuaHang = ch;
        } else {
            this.cuaHang = null;
        }
    }
    
    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }
} 
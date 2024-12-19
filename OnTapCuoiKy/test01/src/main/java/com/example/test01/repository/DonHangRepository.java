package com.example.test01.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test01.entity.DonHang;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, String> {
    @Query("SELECT d FROM DonHang d LEFT JOIN d.cuaHang c " +
           "WHERE d.maDonHang LIKE %:keyword% OR c.ten LIKE %:keyword%")
    List<DonHang> findByMaDonHangContainingOrCuaHang_TenContaining(
        @Param("keyword") String keyword);
    
    List<DonHang> findByTrangThai(Integer trangThai);
    
    boolean existsByEmailAndNgayDatHangBetweenAndTrangThai(
        String email, LocalDateTime start, LocalDateTime end, Integer trangThai);
} 
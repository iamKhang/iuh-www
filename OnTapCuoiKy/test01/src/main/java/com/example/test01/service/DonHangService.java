package com.example.test01.service;

import java.util.List;

import com.example.test01.entity.DonHang;

public interface DonHangService {
    List<DonHang> getAllDonHang();
    List<DonHang> searchDonHang(String keyword);
    DonHang saveDonHang(DonHang donHang);
    void deleteDonHang(String id);
    List<DonHang> getDonHangTam();
    boolean existsByEmailAndDate(String email);
} 
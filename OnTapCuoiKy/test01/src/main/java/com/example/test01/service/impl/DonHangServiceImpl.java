package com.example.test01.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test01.entity.DonHang;
import com.example.test01.repository.DonHangRepository;
import com.example.test01.service.DonHangService;

@Service
public class DonHangServiceImpl implements DonHangService {
    
    @Autowired
    private DonHangRepository donHangRepository;
    
    @Override
    public List<DonHang> getAllDonHang() {
        return donHangRepository.findAll();
    }
    
    @Override
    public List<DonHang> searchDonHang(String keyword) {
        return donHangRepository.findByMaDonHangContainingOrCuaHang_TenContaining(keyword);
    }
    
    @Override
    public DonHang saveDonHang(DonHang donHang) {
        return donHangRepository.save(donHang);
    }
    
    @Override
    public void deleteDonHang(String id) {
        DonHang donHang = donHangRepository.findById(id).orElse(null);
        if (donHang != null && donHang.getTrangThai() == 0) {
            donHangRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Không thể xóa đơn hàng có trạng thái khác 0");
        }
    }
    
    @Override
    public List<DonHang> getDonHangTam() {
        return donHangRepository.findByTrangThai(0);
    }
    
    @Override
    public boolean existsByEmailAndDate(String email) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        
        return donHangRepository.existsByEmailAndNgayDatHangBetweenAndTrangThai(
            email, startOfDay, endOfDay, 1);
    }
} 
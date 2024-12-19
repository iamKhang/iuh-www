package com.example.test01.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test01.entity.DonHang;
import com.example.test01.repository.CuaHangRepository;
import com.example.test01.service.DonHangService;

import jakarta.validation.Valid;

@Controller
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/don-hang/list";
    }

    @GetMapping("/don-hang/list")
    public String listDonHang(Model model) {
        model.addAttribute("donHangs", donHangService.getAllDonHang());
        return "list-don-hang";
    }

    @GetMapping("/don-hang/search")
    public String searchDonHang(@RequestParam String keyword, Model model) {
        model.addAttribute("donHangs", donHangService.searchDonHang(keyword));
        return "list-don-hang";
    }

    @GetMapping("/don-hang/add")
    public String showAddForm(Model model) {
        model.addAttribute("donHang", new DonHang());
        model.addAttribute("dsCuaHang", cuaHangRepository.findAll());
        return "add-don-hang";
    }

    @PostMapping("/don-hang/save")
    public String saveDonHang(@Valid @ModelAttribute DonHang donHang,
                             BindingResult result, Model model) {
        // Kiểm tra validation
        if (result.hasErrors()) {
            model.addAttribute("dsCuaHang", cuaHangRepository.findAll());
            return "add-don-hang";
        }
        
        // Kiểm tra email đã đặt hàng trong ngày chưa
        if (donHangService.existsByEmailAndDate(donHang.getEmail())) {
            result.rejectValue("email", "error.email", 
                "Email này đã đặt một đơn hàng trong ngày hôm nay");
            model.addAttribute("dsCuaHang", cuaHangRepository.findAll());
            return "add-don-hang";
        }
        
        // Tạo ID
        donHang.setId(UUID.randomUUID().toString());
        
        // Tạo mã đơn hàng tự động
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String maDonHang = LocalDateTime.now().format(formatter);
        donHang.setMaDonHang(maDonHang);
        
        donHang.setTrangThai(1);
        
        donHangService.saveDonHang(donHang);
        return "redirect:/don-hang/list";
    }

    @GetMapping("/don-hang/delete/{id}")
    public String deleteDonHang(@PathVariable String id, Model model) {
        try {
            donHangService.deleteDonHang(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/don-hang/list";
    }
} 
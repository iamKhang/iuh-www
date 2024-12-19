package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.AccountRole;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Address;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Company;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.AccountService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.FileStorageService;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder,
            FileStorageService fileStorageService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationOptions() {
        return "register-options";
    }

    @GetMapping("/register/candidate")
    public String showCandidateRegistration(Model model) {
        Account account = new Account();
        Candidate candidate = new Candidate();
        Address address = new Address();
        candidate.setAddress(address);
        account.setCandidate(candidate);
        model.addAttribute("account", account);
        return "register";
    }

    @GetMapping("/register/employer")
    public String showEmployerRegistration(Model model) {
        Account account = new Account();
        Company company = new Company();
        Address address = new Address();
        company.setAddress(address);
        account.setCompany(company);
        model.addAttribute("account", account);
        return "employer-register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") Account account,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (accountService.existsByEmail(account.getCandidate().getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng.");
            return "register";
        }

        try {
            // Kiểm tra xem có file được chọn không
            if (avatarFile == null || avatarFile.isEmpty() || avatarFile.getOriginalFilename().isEmpty()) {
                // Không có file hoặc file rỗng -> dùng ảnh mặc định
                account.getCandidate().setAvatarUrl("/uploads/default_img.png");
            } else {
                // Có file hợp lệ -> lưu file
                String fileName = fileStorageService.saveAvatarFile(avatarFile);
                account.getCandidate().setAvatarUrl("/uploads/avatars/" + fileName);
            }

            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setRole(AccountRole.CANDIDATE);
            accountService.save(account);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi đăng ký.");
            return "register";
        }
    }

    @PostMapping("/register/employer")
    public String registerEmployer(@Valid @ModelAttribute("account") Account account,
            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employer-register";
        }

        if (accountService.existsByEmail(account.getCompany().getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng.");
            return "employer-register";
        }

        try {
            // Kiểm tra xem có file được chọn không
            if (logoFile == null || logoFile.isEmpty() || logoFile.getOriginalFilename().isEmpty()) {
                // Không có file hoặc file rỗng -> dùng ảnh mặc định
                account.getCompany().setLogoUrl("/uploads/default_img.png");
            } else {
                // Có file hợp lệ -> lưu file
                String fileName = fileStorageService.saveLogoFile(logoFile);
                account.getCompany().setLogoUrl("/uploads/logos/" + fileName);
            }

            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setRole(AccountRole.EMPLOYER);
            accountService.save(account);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi đăng ký.");
            return "employer-register";
        }
    }
}

// @PostMapping("/register/employer")
// public String registerEmployer(@Valid @ModelAttribute("account") Account
// account,
// @RequestParam("logoFile") MultipartFile logoFile,
// BindingResult result, Model model) {
// if (result.hasErrors()) {
// return "employer-register";
// }

// if (accountService.existsByEmail(account.getCompany().getEmail())) {
// model.addAttribute("emailError", "Email đã được sử dụng.");
// return "employer-register";
// }

// try {
// // Lưu file logo
// String fileName = fileStorageService.saveLogoFile(logoFile);

// // Set logo URL cho company
// account.getCompany().setLogoUrl("/uploads/logos/" + fileName);

// // Set role và mã hóa password
// account.setPassword(passwordEncoder.encode(account.getPassword()));
// account.setRole(AccountRole.EMPLOYER);

// // Lưu account
// accountService.save(account);

// return "redirect:/login";
// } catch (Exception e) {
// model.addAttribute("error", "Có lỗi xảy ra khi tải lên logo.");
// return "employer-register";
// }
// }
// }

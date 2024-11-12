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
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Address;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.AccountService;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncode;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncode) {
        this.accountService = accountService;
        this.passwordEncode = passwordEncode;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Account account = new Account();
        Candidate candidate = new Candidate();
        Address address = new Address();
        candidate.setAddress(address);
        account.setCandidate(candidate);
        model.addAttribute("account", account);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (accountService.existsByPhone(account.getCandidate().getPhone())) {
            model.addAttribute("phoneError", "Số điện thoại đã được sử dụng.");
            return "register";
        }

        if (accountService.existsByEmail(account.getCandidate().getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng.");
            return "register";
        }

        account.setPassword(passwordEncode.encode(account.getPassword()));

        accountService.register(account);
        return "redirect:/login";
    }
}

package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.JobMatchingService;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CandidateService;

@Controller
public class HomeController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMatchingService jobMatchingService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // Kiểm tra nếu user đã đăng nhập
        if (principal != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYER"))) {
                return "redirect:/employer/dashboard";
            }
        }

        // Lấy 6 việc làm mới nhất
        List<Job> recentJobs = jobRepository.findTop6ByActiveOrderByStartDateDesc(true);
        model.addAttribute("recentJobs", recentJobs);

        // Lấy thống kê theo loại công việc
        List<Object[]> jobTypeStats = jobRepository.countTopJobTypes();
        model.addAttribute("jobTypeStats", jobTypeStats);

        return "index";
    }
} 
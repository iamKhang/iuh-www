// CandidateController.java
package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Experience;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.AccountService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CandidateService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.FileStorageService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.JobMatchingService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.SkillService;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;
    private final SkillService skillService;
    private final JobMatchingService jobMatchingService;
    private final FileStorageService fileStorageService;

    @Autowired
    private AccountService accountService;

    @Autowired
    public CandidateController(CandidateService candidateService, SkillService skillService,
            JobMatchingService jobMatchingService, FileStorageService fileStorageService) {
        this.candidateService = candidateService;
        this.skillService = skillService;
        this.jobMatchingService = jobMatchingService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("")
    public String showCandidateList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "30") int size,
            Model model) {
        Page<Candidate> candidatePage = candidateService.getCandidates(page - 1, size);

        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/candidate-list";
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/updateprofile")
    public String updateProfile(@Valid @ModelAttribute("candidate") Candidate candidate,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkills());
            model.addAttribute("skillLevels", SkillLevel.values());
            return "candidates/update-profile";
        }

        String email = principal.getName();
        Account existingAccount = accountService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Candidate existingCandidate = existingAccount.getCandidate();
        if (existingCandidate == null) {
            return "redirect:/candidates";
        }

        try {
            // Xử lý upload avatar nếu có
            if (avatarFile != null && !avatarFile.isEmpty() && !avatarFile.getOriginalFilename().isEmpty()) {
                String fileName = fileStorageService.saveAvatarFile(avatarFile);
                existingCandidate.setAvatarUrl("/uploads/avatars/" + fileName);
            } else {
                // Nếu không có file mới, giữ nguyên avatarUrl cũ
                existingCandidate.setAvatarUrl(candidate.getAvatarUrl());
            }

            // Cập nhật các thông tin khác
            existingCandidate.setFullName(candidate.getFullName());
            existingCandidate.setPhone(candidate.getPhone());
            existingCandidate.setEmail(candidate.getEmail());
            existingCandidate.setDob(candidate.getDob());

            if (existingCandidate.getAddress() != null) {
                existingCandidate.getAddress().setCountry(candidate.getAddress().getCountry());
                existingCandidate.getAddress().setCity(candidate.getAddress().getCity());
                existingCandidate.getAddress().setStreet(candidate.getAddress().getStreet());
                existingCandidate.getAddress().setNumber(candidate.getAddress().getNumber());
                existingCandidate.getAddress().setZipcode(candidate.getAddress().getZipcode());
            } else {
                existingCandidate.setAddress(candidate.getAddress());
            }

            existingCandidate.getCandidateSkills().clear();
            if (candidate.getCandidateSkills() != null) {
                for (CandidateSkill skill : candidate.getCandidateSkills()) {
                    skill.setCandidate(existingCandidate);
                    existingCandidate.getCandidateSkills().add(skill);
                }
            }

            existingCandidate.getExperiences().clear();
            if (candidate.getExperiences() != null) {
                for (Experience exp : candidate.getExperiences()) {
                    exp.setCandidate(existingCandidate);
                    existingCandidate.getExperiences().add(exp);
                }
            }

            candidateService.saveCandidate(existingCandidate);
            return "redirect:/candidates";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin.");
            return "candidates/update-profile";
        }
    }

    @GetMapping("/updateprofile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String showUpdateProfileForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        Candidate candidate = candidateService.findByEmail(principal.getName());
        if (candidate == null) {
            return "redirect:/candidates";
        }

        model.addAttribute("candidate", candidate);
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("skillLevels", SkillLevel.values());
        
        return "candidates/update-profile";
    }

    @GetMapping("/recommendations")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String showJobRecommendations(Model model, Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        if (principal == null) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByEmail(principal.getName());
        if (candidate == null) {
            return "redirect:/candidates";
        }

        List<JobMatchingService.JobRecommendation> allRecommendations = 
            jobMatchingService.matchCandidateWithJobs(candidate);

        // Phân trang
        int start = page * size;
        int end = Math.min(start + size, allRecommendations.size());
        List<JobMatchingService.JobRecommendation> pageRecommendations = 
            allRecommendations.subList(start, end);

        model.addAttribute("recommendations", pageRecommendations);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil(allRecommendations.size() / (double) size));

        return "candidates/job-recommentdations";
    }
}

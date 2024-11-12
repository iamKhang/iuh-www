// CandidateController.java
package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Experience;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CandidateService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.RecommendationService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.SkillService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;
    private final SkillService skillService;
    private final RecommendationService recommendationService;

    @Autowired
    public CandidateController(CandidateService candidateService, SkillService skillService, RecommendationService recommendationService) {
        this.candidateService = candidateService;
        this.skillService = skillService;
        this.recommendationService = recommendationService;
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
    @GetMapping("/updateprofile")
    public String showUpdateProfileForm(Model model, Principal principal) {
        String phone = principal.getName(); // Giả sử phone là username
        Candidate candidate = candidateService.findByPhone(phone);
        if (candidate == null) {
            return "redirect:/candidates";
        }

        model.addAttribute("candidate", candidate);
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("skillLevels", SkillLevel.values());
        return "candidates/update-profile";
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/updateprofile")
    public String updateProfile(@Valid @ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkills());
            model.addAttribute("skillLevels", SkillLevel.values());
            return "candidates/update-profile";
        }

        String phone = principal.getName();
        Candidate existingCandidate = candidateService.findByPhone(phone);
        if (existingCandidate == null) {
            return "redirect:/candidates";
        }
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
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/recommendations")
    public String showJobRecommendations(Model model, Principal principal) {
        String phone = principal.getName();
        System.out.println("Phone: " + phone);
        Candidate candidate = candidateService.findByPhone(phone);
        if (candidate == null) {
            return "redirect:/candidates";
        }

        List<RecommendationService.JobRecommendation> recommendations = recommendationService.recommendJobsForCandidate(candidate.getId());
        recommendations.forEach(r -> System.out.println(r.getJob().getName() + ": " + r.getScore()));
        recommendations.forEach(r -> r.setScore(r.getScore() * 100));
        model.addAttribute("recommendations", recommendations);
        return "candidates/job-recommentdations";
    }
}

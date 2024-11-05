package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Experience;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CandidateService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.SkillService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/candidates")
    public String showCandidateList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
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



    @GetMapping("/candidates/add")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());

        // Lấy danh sách kỹ năng và cấp độ kỹ năng
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        model.addAttribute("skillLevels", SkillLevel.values());

        return "candidates/add-candidate";
    }

    @PostMapping("/candidates/add")
    public String addCandidate(@Valid @ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("skills", skillService.getAllSkills());
            model.addAttribute("skillLevels", SkillLevel.values());
            return "candidates/add-candidate";
        }

        if (candidate.getCandidateSkills() != null) {
            System.out.printf("Candidate is null");
            for (CandidateSkill skill : candidate.getCandidateSkills()) {
                skill.setCandidate(candidate);
            }
        }

        if (candidate.getExperiences() != null) {
            for (Experience experience : candidate.getExperiences()) {
                System.out.println("From date: " + experience.getFromDate());
                System.out.println("To date: " + experience.getToDate());
                experience.setCandidate(candidate);
            }
        }

        candidateService.saveCandidate(candidate);
        return "redirect:/candidates";
    }


}


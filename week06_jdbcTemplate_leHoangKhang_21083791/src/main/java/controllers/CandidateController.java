package controllers;

import entities.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.CandidateService;
import services.SkillService;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private SkillService skillService;


    @GetMapping
    public String getAllCandidates(Model model) {
        model.addAttribute("candidates", candidateService.getAllCandidates());
        return "candidates";
    }

    @GetMapping("/new")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        model.addAttribute("skills", skillService.getAllSkills());
        return "add_candidate";
    }

    @PostMapping
    public String addCandidate(@ModelAttribute Candidate candidate) {
        candidateService.addCandidate(candidate, null);
        return "redirect:/candidates";
    }

    @GetMapping("/edit/{id}")
    public String showEditCandidateForm(@PathVariable int id, Model model) {
        Candidate candidate = candidateService.getCandidateById(id);
        model.addAttribute("candidate", candidate);
        model.addAttribute("skills", skillService.getAllSkills());
        return "add_candidate";
    }

    @PostMapping("/edit/{id}")
    public String updateCandidate(@PathVariable int id, @ModelAttribute Candidate candidate) {
        candidate.setId(id);
        candidateService.updateCandidate(candidate, null);
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable int id) {
        candidateService.deleteCandidate(id);
        return "redirect:/candidates";
    }
}


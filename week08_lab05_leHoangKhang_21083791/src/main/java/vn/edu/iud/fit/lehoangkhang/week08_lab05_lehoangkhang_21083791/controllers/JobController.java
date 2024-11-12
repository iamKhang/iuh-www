package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Company;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.JobSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.JobService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CompanyService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.SkillService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SkillService skillService;

    @GetMapping("")
    public String showJobList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Page<Job> jobPage = jobService.getJobs(page - 1, size);

        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "jobs/job-list";
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/add")
    public String showAddJobForm(Model model) {
        Job job = new Job();
        job.setJobSkills(new ArrayList<>());
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("job", job);
        model.addAttribute("skills", skills);
        model.addAttribute("skillLevels", SkillLevel.values());
        return "jobs/add-job";
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job) {
        Company company = companyService.findById(1L); // Default to company with ID 1
        job.setCompany(company);

        List<JobSkill> jobSkills = job.getJobSkills();
        for (JobSkill jobSkill : jobSkills) {
            jobSkill.setJob(job);
        }
        jobService.addJob(job);
        return "redirect:/jobs";
    }
}

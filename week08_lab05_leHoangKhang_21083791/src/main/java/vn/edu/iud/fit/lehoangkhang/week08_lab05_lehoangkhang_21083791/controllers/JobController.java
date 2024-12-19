package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.JobType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.JobSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.AccountService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.CandidateService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.JobApplicationService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.JobService;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services.SkillService;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService applicationService;
    
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkillService skillService;

    @GetMapping("")
    public String showJobList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String salaryRange,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        // Thêm dữ liệu cho các dropdown
        model.addAttribute("cities", jobService.getDistinctCities());
        model.addAttribute("jobTypes", jobService.getDistinctJobTypes());
        
        // Thêm các tùy chọn lương
        model.addAttribute("salaryRanges", Arrays.asList(
            "0-10", "10-20", "20-30", "30-40", "40+", "negotiable"
        ));

        // Thực hiện tìm kiếm
        Page<Job> jobPage = jobService.searchJobs(keyword, city, jobType, salaryRange, page, size);
        
        // Thêm kết quả và các tham số tìm kiếm vào model
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCity", city);
        model.addAttribute("selectedJobType", jobType);
        model.addAttribute("selectedSalaryRange", salaryRange);
        
        return "jobs/job-list";
    }

    @GetMapping("/{id}")
    public String getJobDetails(@PathVariable Long id, Model model, Principal principal) {
        Job job = jobService.getJobById(id)
            .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        boolean hasApplied = false;
        if (principal != null) {
            String email = principal.getName();
            Candidate candidate = candidateService.findByEmail(email);
            hasApplied = applicationService.hasApplied(candidate, id);
        }
        
        model.addAttribute("job", job);
        model.addAttribute("hasApplied", hasApplied);
        return "jobs/job-detail";
    }

    @PostMapping("/{id}/apply")
    public String applyForJob(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            // Lưu URL hiện tại vào session để redirect sau khi đăng nhập
            return "redirect:/login";
        }

        try {
            String phone = principal.getName();
            System.out.println("Phone: "+phone);
            Candidate candidate = candidateService.findByEmail(phone);
            
            boolean success = applicationService.applyForJob(candidate, id);
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "Ứng tuyển thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Bạn đã ứng tuyển công việc này rồi!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi ứng tuyển!");
        }
        
        return "redirect:/jobs/" + id;
    }

//    @PreAuthorize("hasRole('EMPLOYER')")
//    @GetMapping("/post")
//    public String showPostJobForm(Model model) {
//        model.addAttribute("job", new Job());
//        model.addAttribute("jobTypes", JobType.values());
//        model.addAttribute("skills", skillService.getAllSkills());
//        model.addAttribute("skillLevels", SkillLevel.values());
//        return "jobs/add-job";
//    }
@PreAuthorize("hasRole('EMPLOYER')")
@PostMapping("/post")
public String postJob(@Valid @ModelAttribute Job job, 
                     @RequestParam("deadlineStr") String deadlineStr,
                     BindingResult result,
                     Principal principal, 
                     Model model) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate deadline = LocalDate.parse(deadlineStr, formatter);
        job.setDeadline(deadline);

        if (job.isNegotiable()) {
            job.setSalaryFrom(0);
            job.setSalaryTo(0);
        } else {
            if (job.getSalaryFrom() <= 0 || job.getSalaryTo() <= 0 ||
                    job.getSalaryFrom() > job.getSalaryTo()) {
                result.rejectValue("salaryFrom", "error.salary", "Mức lương không hợp lệ");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("jobTypes", JobType.values());
            model.addAttribute("skills", skillService.getAllSkills());
            model.addAttribute("skillLevels", SkillLevel.values());
            return "jobs/add-job";
        }

        Account account = accountService.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        job.setCompany(account.getCompany());
        job.setStartDate(LocalDate.now());
        job.setActive(true);
        job.setNumberOfApplicants(0);

        if (job.getJobSkills() != null) {
            for (JobSkill skill : job.getJobSkills()) {
                skill.setJob(job);
            }
        }

        jobService.saveJob(job);
        return "redirect:/jobs";
    } catch (Exception e) {
        model.addAttribute("error", "Có lỗi xảy ra khi đăng tin tuyển dụng");
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("skills", skillService.getAllSkills());
        model.addAttribute("skillLevels", SkillLevel.values());
        return "jobs/add-job";
    }
}


}

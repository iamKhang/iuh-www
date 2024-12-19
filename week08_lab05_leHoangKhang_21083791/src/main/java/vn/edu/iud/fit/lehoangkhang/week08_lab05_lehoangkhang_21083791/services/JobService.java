package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateApplyJob;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.JobType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Page<Job> getJobs(int page, int size) {
        return jobRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public long countActiveJobsByCompany(Long companyId) {
        return jobRepository.countByCompanyIdAndActiveTrue(companyId);
    }

    public long countTotalApplicationsByCompany(Long companyId) {
        return jobRepository.countTotalApplicationsByCompanyId(companyId);
    }

    public long countExpiringJobsByCompany(Long companyId) {
        LocalDate oneWeekFromNow = LocalDate.now().plusDays(7);
        return jobRepository.countExpiringJobs(companyId, oneWeekFromNow);
    }

    public List<CandidateApplyJob> getRecentApplicationsByCompany(Long companyId) {
        return jobRepository.findRecentApplicationsByCompanyId(companyId, PageRequest.of(0, 5));
    }

    public List<Job> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId);
    }

    public List<CandidateApplyJob> getAllApplicationsByCompany(Long companyId) {
        return jobRepository.findAllApplicationsByCompanyId(companyId);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public List<CandidateApplyJob> getApplicationsByJob(Long jobId) {
        return jobRepository.findCandidateApplyJobsByJobId(jobId);
    }

    public List<String> getDistinctCities() {
        return jobRepository.findDistinctCities();
    }

    public List<JobType> getDistinctJobTypes() {
        return Arrays.asList(JobType.values());
    }

    public Page<Job> searchJobs(String keyword, String city, String jobType, 
                              String salaryRange, int page, int size) {
        
        // Xử lý keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
        } else {
            keyword = null;
        }

        // Xử lý city
        if (city != null && city.trim().isEmpty()) {
            city = null;
        }

        // Xử lý jobType
        if (jobType != null && jobType.trim().isEmpty()) {
            jobType = null;
        }

        Double minSalary = null;
        Double maxSalary = null;
        Boolean negotiable = null;

        if (salaryRange != null && !salaryRange.isEmpty()) {
            if (salaryRange.equals("negotiable")) {
                negotiable = true;
            } else if (salaryRange.equals("20+")) {
                minSalary = 20000000.0;
            } else {
                String[] range = salaryRange.split("-");
                minSalary = Double.parseDouble(range[0]) * 1000000;
                maxSalary = Double.parseDouble(range[1]) * 1000000;
            }
        }

        return jobRepository.searchJobs(
            keyword,
            city,
            jobType,
            minSalary,
            maxSalary,
            negotiable,
            PageRequest.of(page, size)
        );
    }

    public List<Job> getActiveJobsByCompany(Long companyId) {
        return jobRepository.findTop10ByCompanyIdAndActiveTrueOrderByStartDateDesc(companyId);
    }
}

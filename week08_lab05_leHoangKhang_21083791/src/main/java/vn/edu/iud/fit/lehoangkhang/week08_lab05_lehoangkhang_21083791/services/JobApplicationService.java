package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateApplyJob;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateApplyJobRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;

@Service
public class JobApplicationService {

    @Autowired
    private CandidateApplyJobRepository applyJobRepository;
    
    @Autowired
    private JobRepository jobRepository;

    @Transactional
    public boolean applyForJob(Candidate candidate, Long jobId) {
        try {
            Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
            System.out.println("Job: " + job.getId());
            
            // Kiểm tra xem đã ứng tuyển chưa
            if (applyJobRepository.existsByCandidateAndJob(candidate, job)) {
                return false;
            }

            // Tạo bản ghi ứng tuyển mới
            CandidateApplyJob application = new CandidateApplyJob();
            application.setCandidate(candidate);
            application.setJob(job);
            application.setApplyDate(LocalDate.now());
            
            // Lưu bản ghi ứng tuyển
            applyJobRepository.save(application);

            // Tăng số lượng ứng viên
            job.setNumberOfApplicants(job.getNumberOfApplicants() + 1);
            jobRepository.save(job);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasApplied(Candidate candidate, Long jobId) {
        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));
//        if (applyJobRepository.existsByCandidateAndJob(candidate, job)) {
//            System.out.println("Candidate: " + candidate.getId());
//            System.out.println("Job: " + job.getId());
//            System.out.println("Has applied");
//        }else {
//            System.out.println("Candidate: " + candidate.getId());
//            System.out.println("Job: " + job.getId());
//            System.out.println("Has not applied");
//        }
        return applyJobRepository.existsByCandidateAndJob(candidate, job);
    }
} 
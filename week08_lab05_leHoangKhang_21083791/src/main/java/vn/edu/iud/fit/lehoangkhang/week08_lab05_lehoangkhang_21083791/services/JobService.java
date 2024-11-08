package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public boolean addJob(Job job) {
        try {
            jobRepository.save(job);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Page<Job> getJobs(int i, int size) {
        return jobRepository.findAll(PageRequest.of(i, size));
    }
}

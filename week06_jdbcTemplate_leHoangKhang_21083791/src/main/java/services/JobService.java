package services;


import entities.Job;
import entities.JobSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.JobRepository;
import repositories.JobSkillRepository;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void addJob(Job job, List<JobSkill> jobSkills) {
        jobRepository.save(job);
        for (JobSkill skill : jobSkills) {
            skill.setJobId(job.getId());
            jobSkillRepository.save(skill);
        }
    }

    public void updateJob(Job job, List<JobSkill> jobSkills) {
        jobRepository.update(job);

        List<JobSkill> currentSkills = jobSkillRepository.findByJobId(job.getId());

        for (JobSkill currentSkill : currentSkills) {
            if (!jobSkills.contains(currentSkill)) {
                jobSkillRepository.delete(currentSkill.getJobId(), currentSkill.getSkillId());
            }
        }

        for (JobSkill newSkill : jobSkills) {
            if (currentSkills.contains(newSkill)) {
                jobSkillRepository.update(newSkill);
            } else {
                newSkill.setJobId(job.getId());
                jobSkillRepository.save(newSkill);
            }
        }
    }


    // Xóa công việc và kỹ năng yêu cầu
    public void deleteJob(int id) {
        jobSkillRepository.deleteByJobId(id);  // Xóa các kỹ năng của công việc trước
        jobRepository.delete(id);  // Xóa công việc
    }

    // Lấy thông tin công việc theo ID
    public Job getJobById(int id) {
        return jobRepository.findAll().stream()
                .filter(job -> job.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Lấy các kỹ năng yêu cầu của công việc
    public List<JobSkill> getJobSkills(int jobId) {
        return jobSkillRepository.findByJobId(jobId);
    }
}

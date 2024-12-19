package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.JobSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {

    private final JobRepository jobRepository;

    public JobMatchingService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobRecommendation> matchCandidateWithJobs(Candidate candidate) {
        // Trọng số cho từng cấp độ kỹ năng
        Map<SkillLevel, Integer> skillWeightMap = Map.of(
                SkillLevel.BEGINNER, 1,
                SkillLevel.INTERMEDIATE, 2,
                SkillLevel.PROFESSIONAL, 3,
                SkillLevel.ADVANCED, 4,
                SkillLevel.MASTER, 5
        );

        // Ánh xạ kỹ năng của ứng viên thành Map<SkillId, SkillLevel>
        Map<Long, SkillLevel> candidateSkillMap = candidate.getCandidateSkills().stream()
                .collect(Collectors.toMap(
                        candidateSkill -> candidateSkill.getSkill().getId(),
                        CandidateSkill::getSkillLevel
                ));

        // Lấy danh sách công việc và kỹ năng
        List<Job> jobs = jobRepository.findAllActiveJobsWithSkills();

        // Tính toán điểm cho từng công việc
        List<JobRecommendation> recommendations = new ArrayList<>();

        for (Job job : jobs) {
            int totalScore = 0;

            for (JobSkill jobSkill : job.getJobSkills()) {
                Long skillId = jobSkill.getSkill().getId();
                if (candidateSkillMap.containsKey(skillId)) {
                    int candidateWeight = skillWeightMap.get(candidateSkillMap.get(skillId));
                    int jobWeight = skillWeightMap.get(jobSkill.getSkillLevel());
                    totalScore += candidateWeight * jobWeight;
                }
            }

            if (totalScore > 0) { // Chỉ thêm công việc có điểm số > 0
                recommendations.add(new JobRecommendation(job, totalScore));
            }
        }

        // Sắp xếp công việc theo điểm số từ cao xuống thấp
        recommendations.sort(Comparator.comparingInt(JobRecommendation::getScore).reversed());

        return recommendations;
    }

    // Inner class để chứa thông tin gợi ý
    public static class JobRecommendation {
        private final Job job;
        private final int score;

        public JobRecommendation(Job job, int score) {
            this.job = job;
            this.score = score;
        }

        public Job getJob() {
            return job;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            // Nếu cần chỉnh sửa điểm sau này
            // Trong trường hợp hiện tại, setter không cần thiết
        }
    }
}

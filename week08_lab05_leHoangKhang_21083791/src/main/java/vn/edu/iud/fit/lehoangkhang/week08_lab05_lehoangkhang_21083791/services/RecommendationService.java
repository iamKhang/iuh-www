// RecommendationService.java
package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.utils.SimilarityCalculator;

import java.util.*;

@Service
public class RecommendationService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillService skillService;

    /**
     * Gợi ý các công việc phù hợp cho ứng viên dựa trên Cosine Similarity.
     *
     * @param candidateId ID của ứng viên.
     * @return Danh sách các công việc được sắp xếp theo mức độ phù hợp giảm dần.
     */
    public List<JobRecommendation> recommendJobsForCandidate(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            throw new RuntimeException("Candidate not found");
        }

        // Lấy danh sách tất cả các kỹ năng
        List<Skill> allSkills = skillService.getAllSkills();

        // Tạo vector kỹ năng cho ứng viên
        double[] candidateVector = SimilarityCalculator.createSkillVector(allSkills, candidate.getCandidateSkills());

        // Lấy tất cả các công việc
        List<Job> allJobs = jobRepository.findAll();

        // Tính similarity score cho từng công việc
        List<JobRecommendation> recommendations = new ArrayList<>();

        for (Job job : allJobs) {
            double[] jobVector = SimilarityCalculator.createSkillVector(allSkills, job.getJobSkills());
            double score = SimilarityCalculator.cosineSimilarity(candidateVector, jobVector);
            recommendations.add(new JobRecommendation(job, score));
        }

        // Sắp xếp các công việc theo điểm tương đồng giảm dần
        recommendations.sort((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()));

        return recommendations;
    }

    /**
     * Lớp đại diện cho một đề xuất công việc cùng với điểm tương đồng.
     */
    public static class JobRecommendation {
        private Job job;
        private double score;

        public JobRecommendation(Job job, double score) {
            this.job = job;
            this.score = score;
        }

        public Job getJob() {
            return job;
        }

        public double getScore() {
            return score;
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}

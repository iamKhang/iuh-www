package services;

import entities.Candidate;
import entities.CandidateSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CandidateRepository;
import repositories.CandidateSkillRepository;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;


    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public void addCandidate(Candidate candidate, List<CandidateSkill> skills) {
        candidateRepository.save(candidate);
        for (CandidateSkill skill : skills) {
            skill.setCandidateId(candidate.getId());
            candidateSkillRepository.save(skill);
        }
    }

    public void updateCandidate(Candidate candidate, List<CandidateSkill> newSkills) {
        candidateRepository.update(candidate);

        List<CandidateSkill> currentSkills = candidateSkillRepository.findByCandidateId(candidate.getId());

        for (CandidateSkill currentSkill : currentSkills) {
            if (!newSkills.contains(currentSkill)) {
                candidateSkillRepository.delete(currentSkill.getCandidateId(), currentSkill.getSkillId());
            }
        }

        for (CandidateSkill newSkill : newSkills) {
            if (currentSkills.contains(newSkill)) {
                candidateSkillRepository.update(newSkill);
            } else {
                newSkill.setCandidateId(candidate.getId());
                candidateSkillRepository.save(newSkill);
            }
        }
    }


    public void deleteCandidate(int id) {
        candidateSkillRepository.deleteByCandidateId(id);
        candidateRepository.delete(id);  // Xóa ứng viên
    }

    // Lấy thông tin ứng viên theo ID
    public Candidate getCandidateById(int id) {
        return candidateRepository.findAll().stream()
                .filter(candidate -> candidate.getId() == id)
                .findFirst()
                .orElse(null);
    }
}

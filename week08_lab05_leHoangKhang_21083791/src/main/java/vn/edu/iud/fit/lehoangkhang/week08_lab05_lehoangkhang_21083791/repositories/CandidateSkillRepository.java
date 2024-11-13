package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;

import java.util.List;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    List<CandidateSkill> findByCandidateId(Long id);

    @Query("SELECT cs.skill.type, COUNT(DISTINCT cs.candidate.id) " +
            "FROM CandidateSkill cs " +
            "GROUP BY cs.skill.type")
    List<Object[]> countCandidatesBySkillType();
}

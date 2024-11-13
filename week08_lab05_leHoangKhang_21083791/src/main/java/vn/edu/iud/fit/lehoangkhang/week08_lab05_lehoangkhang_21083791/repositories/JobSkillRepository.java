package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.JobSkill;

import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    List<JobSkill> findByJobId(Long id);
    @Query("SELECT js.skill.skillName, COUNT(js.id) as count " +
            "FROM JobSkill js WHERE js.skill.type = :skillType " +
            "GROUP BY js.skill.skillName " +
            "ORDER BY count DESC")
    List<Object[]> findTopSkillsByType(@Param("skillType") SkillType skillType, Pageable pageable);
}

package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findBySkillName(String type);
}

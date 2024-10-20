package repositories;

import entities.CandidateSkill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class CandidateSkillRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CandidateSkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CandidateSkill> findAll() {
        String sql = "SELECT * FROM Candidate_Skill";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CandidateSkill(
                        rs.getInt("candidate_id"),
                        rs.getInt("skill_id"),
                        rs.getInt("level")
                )
        );
    }

    public int save(CandidateSkill candidateSkill) {
        String sql = "INSERT INTO Candidate_Skill (candidate_id, skill_id, level) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, candidateSkill.getCandidateId(), candidateSkill.getSkillId(), candidateSkill.getLevel());
    }

    public int update(CandidateSkill candidateSkill) {
        String sql = "UPDATE Candidate_Skill SET level = ? WHERE candidate_id = ? AND skill_id = ?";
        return jdbcTemplate.update(sql, candidateSkill.getLevel(), candidateSkill.getCandidateId(), candidateSkill.getSkillId());
    }

    public int delete(int candidateId, int skillId) {
        String sql = "DELETE FROM Candidate_Skill WHERE candidate_id = ? AND skill_id = ?";
        return jdbcTemplate.update(sql, candidateId, skillId);
    }

    public List<CandidateSkill> findByCandidateId(int id) {
        String sql = "SELECT * FROM Candidate_Skill WHERE candidate_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new CandidateSkill(
                        rs.getInt("candidate_id"),
                        rs.getInt("skill_id"),
                        rs.getInt("level")
                )
        );
    }

    public void deleteByCandidateId(int id) {
        String sql = "DELETE FROM Candidate_Skill WHERE candidate_id = ?";
        jdbcTemplate.update(sql, id);
    }
}

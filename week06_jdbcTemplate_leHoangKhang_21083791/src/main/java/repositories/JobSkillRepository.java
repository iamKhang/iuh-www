package repositories;


import entities.JobSkill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class JobSkillRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobSkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<JobSkill> findAll() {
        String sql = "SELECT * FROM Job_Skill";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new JobSkill(
                        rs.getInt("job_id"),
                        rs.getInt("skill_id"),
                        rs.getInt("level_required")
                )
        );
    }

    public int save(JobSkill jobSkill) {
        String sql = "INSERT INTO Job_Skill (job_id, skill_id, level_required) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, jobSkill.getJobId(), jobSkill.getSkillId(), jobSkill.getLevelRequired());
    }

    public int update(JobSkill jobSkill) {
        String sql = "UPDATE Job_Skill SET level_required = ? WHERE job_id = ? AND skill_id = ?";
        return jdbcTemplate.update(sql, jobSkill.getLevelRequired(), jobSkill.getJobId(), jobSkill.getSkillId());
    }

    public int delete(int jobId, int skillId) {
        String sql = "DELETE FROM Job_Skill WHERE job_id = ? AND skill_id = ?";
        return jdbcTemplate.update(sql, jobId, skillId);
    }

    public List<JobSkill> findByJobId(int id) {
        String sql = "SELECT * FROM Job_Skill WHERE job_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new JobSkill(
                        rs.getInt("job_id"),
                        rs.getInt("skill_id"),
                        rs.getInt("level_required")
                )
        );
    }

    public void deleteByJobId(int id) {
        String sql = "DELETE FROM Job_Skill WHERE job_id = ?";
        jdbcTemplate.update(sql, id);
    }
}

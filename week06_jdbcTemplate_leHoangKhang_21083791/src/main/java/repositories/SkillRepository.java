package repositories;

import entities.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class SkillRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SkillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Skill> findAll() {
        String sql = "SELECT * FROM Skill";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Skill(
                        rs.getInt("id"),
                        rs.getString("skill_name"),
                        rs.getString("description"),
                        rs.getString("field")
                )
        );
    }

    public int save(Skill skill) {
        String sql = "INSERT INTO Skill (skill_name, description, field) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, skill.getSkillName(), skill.getDescription(), skill.getField());
    }

    public int update(Skill skill) {
        String sql = "UPDATE Skill SET skill_name = ?, description = ?, field = ? WHERE id = ?";
        return jdbcTemplate.update(sql, skill.getSkillName(), skill.getDescription(), skill.getField(), skill.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM Skill WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

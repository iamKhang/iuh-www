package repositories;

import entities.Job;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Repository
public class JobRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Job> findAll() {
        String sql = "SELECT * FROM Job";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Job(
                        rs.getInt("id"),
                        rs.getString("description")
                )
        );
    }

    public int save(Job job) {
        String sql = "INSERT INTO Job (description) VALUES (?)";
        return jdbcTemplate.update(sql, job.getDescription());
    }

    public int update(Job job) {
        String sql = "UPDATE Job SET description = ? WHERE id = ?";
        return jdbcTemplate.update(sql, job.getDescription(), job.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM Job WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}


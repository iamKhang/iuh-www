package repositories;


import entities.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CandidateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CandidateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Candidate> findAll() {
        String sql = "SELECT * FROM Candidate";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Candidate(
                        rs.getInt("id"),
                        rs.getString("last_name"),
                        rs.getString("middle_name"),
                        rs.getString("first_name"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone")
                )
        );
    }

    public int save(Candidate candidate) {
        String sql = "INSERT INTO Candidate (last_name, middle_name, first_name, dob, email, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, candidate.getLastName(), candidate.getMiddleName(), candidate.getFirstName(), candidate.getDob(), candidate.getEmail(), candidate.getAddress(), candidate.getPhone());
    }

    public int update(Candidate candidate) {
        String sql = "UPDATE Candidate SET last_name = ?, middle_name = ?, first_name = ?, dob = ?, email = ?, address = ?, phone = ? WHERE id = ?";
        return jdbcTemplate.update(sql, candidate.getLastName(), candidate.getMiddleName(), candidate.getFirstName(), candidate.getDob(), candidate.getEmail(), candidate.getAddress(), candidate.getPhone(), candidate.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM Candidate WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}


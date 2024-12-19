package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByPhone(String phone);
    @Query("SELECT c.address.city, COUNT(c.id) as count " +
            "FROM Candidate c " +
            "GROUP BY c.address.city " +
            "ORDER BY count DESC")
    List<Object[]> findTopCities(Pageable pageable);

    @Query(value = "SELECT " +
            "CASE " +
            "WHEN TIMESTAMPDIFF(YEAR, dob, CURDATE()) < 25 THEN 'Under 25' " +
            "WHEN TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 25 AND 35 THEN '25-35' " +
            "WHEN TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN 36 AND 50 THEN '36-50' " +
            "ELSE 'Above 50' END AS ageGroup, " +
            "COUNT(*) " +
            "FROM Candidate " +
            "GROUP BY ageGroup",
            nativeQuery = true)
    List<Object[]> countCandidatesByAgeGroup();

    @Query("SELECT c.gender, COUNT(c) FROM Candidate c GROUP BY c.gender")
    List<Object[]> countCandidatesByGender();

    Candidate findByEmail(String email);




}

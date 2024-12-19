package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateApplyJob;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.JobType;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j.type, COUNT(j) as count FROM Job j GROUP BY j.type ORDER BY count DESC")
    List<Object[]> countTopJobTypes();
    @Query("SELECT DISTINCT j FROM Job j " +
            "LEFT JOIN FETCH j.jobSkills js " +
            "LEFT JOIN FETCH js.skill " +
            "WHERE j.active = true")
    List<Job> findAllActiveJobsWithSkills();
    List<Job> findTop4ByActiveOrderByStartDateDesc(boolean active);
    long countByCompanyIdAndActiveTrue(Long companyId);
    @Query("SELECT COUNT(a) FROM CandidateApplyJob a WHERE a.job.company.id = :companyId")
    long countTotalApplicationsByCompanyId(Long companyId);
    @Query("SELECT COUNT(j) FROM Job j WHERE j.company.id = :companyId " +
       "AND j.active = true AND j.deadline <= :expirationDate")
long countExpiringJobs(@Param("companyId") Long companyId, 
                      @Param("expirationDate") LocalDate expirationDate);
    List<Job> findByCompanyId(Long companyId);
    @Query("SELECT a FROM CandidateApplyJob a WHERE a.job.company.id = :companyId " +
           "ORDER BY a.applyDate DESC")
    List<CandidateApplyJob> findRecentApplicationsByCompanyId(Long companyId, Pageable pageable);
    @Query("SELECT a FROM CandidateApplyJob a WHERE a.job.company.id = :companyId " +
           "ORDER BY a.applyDate DESC")
    List<CandidateApplyJob> findAllApplicationsByCompanyId(Long companyId);
    @Query("SELECT caj FROM CandidateApplyJob caj " +
           "JOIN FETCH caj.candidate " +
           "WHERE caj.job.id = :jobId " +
           "ORDER BY caj.applyDate DESC")
    List<CandidateApplyJob> findCandidateApplyJobsByJobId(Long jobId);
    List<Job> findTop6ByActiveOrderByStartDateDesc(boolean active);
    @Query("SELECT DISTINCT j.company.address.city FROM Job j WHERE j.active = true ORDER BY j.company.address.city")
    List<String> findDistinctCities();
    @Query("SELECT DISTINCT j.type FROM Job j WHERE j.active = true ORDER BY j.type")
    List<JobType> findDistinctJobTypes();
    @Query(value = "SELECT j.* FROM job j " +
           "JOIN company c ON j.company_id = c.id " +
           "JOIN address a ON c.address_id = a.id " +
           "WHERE j.active = true " +
           "AND (:keyword IS NULL OR LOWER(j.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "    OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (COALESCE(:city, '') = '' OR a.city = :city) " +
           "AND (COALESCE(:jobType, '') = '' OR j.type = :jobType) " +
           "AND (:minSalary IS NULL OR j.salary_from >= :minSalary) " +
           "AND (:maxSalary IS NULL OR j.salary_to <= :maxSalary) " +
           "AND (:negotiable IS NULL OR j.negotiable = :negotiable)",
           countQuery = "SELECT COUNT(*) FROM job j " +
           "JOIN company c ON j.company_id = c.id " +
           "JOIN address a ON c.address_id = a.id " +
           "WHERE j.active = true " +
           "AND (:keyword IS NULL OR LOWER(j.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "    OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (COALESCE(:city, '') = '' OR a.city = :city) " +
           "AND (COALESCE(:jobType, '') = '' OR j.type = :jobType) " +
           "AND (:minSalary IS NULL OR j.salary_from >= :minSalary) " +
           "AND (:maxSalary IS NULL OR j.salary_to <= :maxSalary) " +
           "AND (:negotiable IS NULL OR j.negotiable = :negotiable)",
           nativeQuery = true)
    Page<Job> searchJobs(
        @Param("keyword") String keyword,
        @Param("city") String city,
        @Param("jobType") String jobType,
        @Param("minSalary") Double minSalary,
        @Param("maxSalary") Double maxSalary,
        @Param("negotiable") Boolean negotiable,
        Pageable pageable
    );
    List<Job> findByCompanyIdAndActiveTrue(Long companyId);
    List<Job> findTop10ByCompanyIdAndActiveTrueOrderByStartDateDesc(Long companyId);
}

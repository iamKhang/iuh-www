package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}

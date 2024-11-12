package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Check login if username and password are correct return account id otherwise return null
    @Query("SELECT a.id FROM Account a WHERE a.candidate.email = ?1 AND a.password = ?2")
    Long checkLogin(String username, String password);

    @Query("SELECT a FROM Account a WHERE a.candidate.phone = ?1")
    Optional<Account> findAccountByPhone(String phone);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Account a WHERE a.candidate.phone = ?1")
    boolean existsByPhone(String phone);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Account a WHERE a.candidate.email = ?1")
    boolean existsByEmail(String email);
}

package vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.repositories;

import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwnerNameContaining(String ownerName, PageRequest pageRequest);
}

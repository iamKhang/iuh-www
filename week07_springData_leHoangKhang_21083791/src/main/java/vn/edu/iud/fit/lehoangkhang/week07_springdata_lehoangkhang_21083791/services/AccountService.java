package vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.services;

import org.springframework.data.domain.PageRequest;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.entities.Account;

import java.util.List;

public interface AccountService {
    boolean createAccount(String ownerName, double balance);
    boolean deposit(Long id, double amount);
    boolean withdraw(Long id, double amount);
    boolean transfer(Long fromId, Long toId, double amount);
    boolean deleteAccount(Long id);
    List<Account> getAllAccounts(int page);
    Account getAccountById(Long id);
    List<Account> getAccountsByOwnerName(String ownerName, PageRequest pageRequest);
    List<Account> getAccountsByBalance(double minBalance, double maxBalance, PageRequest pageRequest);
}

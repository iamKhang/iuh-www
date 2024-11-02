package vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.entities.Account;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.repositories.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImplt implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean createAccount(String ownerName, double balance) {
        try {
            Account account = new Account(ownerName, balance);
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return false;
        } else if (amount <= 0) {
            return false;
        } else {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            return true;
        }
    }

    @Override
    public boolean withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return false;
        } else if (amount <= 0 || amount > account.getBalance()) {
            return false;
        } else {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            return true;
        }
    }

    @Override
    public boolean transfer(Long fromId, Long toId, double amount) {
        Account fromAccount = accountRepository.findById(fromId).orElse(null);
        Account toAccount = accountRepository.findById(toId).orElse(null);
        if (fromAccount == null || toAccount == null) {
            return false;
        } else if (amount <= 0 || amount > fromAccount.getBalance()) {
            return false;
        } else {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            return true;
        }
    }

    @Override
    public boolean deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return false;
        } else {
            accountRepository.delete(account);
            return true;
        }
    }

    @Override
    public List<Account> getAllAccounts(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return accountRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAccountsByOwnerName(String ownerName, PageRequest pageRequest) {

        return accountRepository.findByOwnerNameContaining(ownerName, pageRequest);
    }

    @Override
    public List<Account> getAccountsByBalance(double minBalance, double maxBalance, PageRequest pageRequest) {
        return List.of();
    }
}

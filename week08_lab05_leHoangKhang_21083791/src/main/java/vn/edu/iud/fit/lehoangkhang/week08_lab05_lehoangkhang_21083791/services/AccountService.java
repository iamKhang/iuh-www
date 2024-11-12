package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Long checkLogin(String username, String password) {
        return accountRepository.checkLogin(username, password);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void register(Account account) {
        accountRepository.save(account);
    }

    public boolean existsByPhone(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}

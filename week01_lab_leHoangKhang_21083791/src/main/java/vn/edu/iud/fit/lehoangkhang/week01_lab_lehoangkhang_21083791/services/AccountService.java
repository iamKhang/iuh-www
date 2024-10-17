package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.services;

import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Account;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories.AccountRepository;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories.LogRepository;

import java.util.List;

public class AccountService {
    private AccountRepository accountRepository;
    private LogRepository logRepository;

    public AccountService() {
        accountRepository = new AccountRepository();
        logRepository = new LogRepository();
    }

    public int checkLogin(String accountId, String password) {
        int result = accountRepository.checkLogin(accountId, password);
        return result;
    }

    public Account getUser(String accountId) {
        return accountRepository.getAccount(accountId);
    }

    public long writeLogLogin(String accountId) {
        long logId = logRepository.writeLog(accountId);
        return logId;
    }

    public void writeLogLogout(Long logId) {
        logRepository.updateLog(logId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }
}

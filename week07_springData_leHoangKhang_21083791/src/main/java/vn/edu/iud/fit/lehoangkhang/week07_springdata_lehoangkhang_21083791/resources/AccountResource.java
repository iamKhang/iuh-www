package vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.entities.Account;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.services.AccountService;

import java.util.List;

@RestController
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Account> accounts = accountService.getAllAccounts(page);
        return ResponseEntity.ok().body(accounts);
    }

    //    tranfer money
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam double amount) {
        boolean success = accountService.transfer(fromId, toId, amount);
        if (success) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.status(400).body("Transfer failed");
        }
    }

}

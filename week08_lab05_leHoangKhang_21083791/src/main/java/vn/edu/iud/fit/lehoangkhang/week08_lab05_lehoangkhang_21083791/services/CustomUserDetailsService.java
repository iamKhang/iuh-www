package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Account;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.AccountRepository;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;  // Repository để tìm tài khoản

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản"));

        // Tạo danh sách quyền từ vai trò của Account
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + account.getRole().name())
        );

        // Chuyển đổi thành đối tượng UserDetails
        return new org.springframework.security.core.userdetails.User(
                account.getCandidate().getPhone(),
                account.getPassword(),
                authorities
        );
    }
}

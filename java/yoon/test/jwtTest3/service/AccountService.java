package yoon.test.jwtTest3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yoon.test.jwtTest3.dto.AccountDto;
import yoon.test.jwtTest3.entity.Account;
import yoon.test.jwtTest3.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Account findAccount(String username){
        try {
            return accountRepository.findAccountByUsername(username);
        }catch(UsernameNotFoundException e){
            return new Account(null, null);
        }
    }

    public void join(AccountDto dto){
        Account account = new Account(dto.getUsername(), passwordEncoder.encode(dto.getPassword()));
        accountRepository.save(account);
    }
}

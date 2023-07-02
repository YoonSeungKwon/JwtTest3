package yoon.test.jwtTest3.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.BcelAnnotation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yoon.test.jwtTest3.dto.AccountDto;
import yoon.test.jwtTest3.entity.Account;
import yoon.test.jwtTest3.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findAccountByUsername(username);

        if(account == null)
            throw new UsernameNotFoundException(username);


        return account;
    }
}

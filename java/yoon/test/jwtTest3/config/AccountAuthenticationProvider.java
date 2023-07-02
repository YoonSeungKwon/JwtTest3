package yoon.test.jwtTest3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import yoon.test.jwtTest3.service.AccountDetailService;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider implements AuthenticationProvider {

    private final AccountDetailService accountDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails user = accountDetailService.loadUserByUsername(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new UsernameNotFoundException("비밀번호가 일치하지 않습니다.");
        }
        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

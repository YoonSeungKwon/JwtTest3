package yoon.test.jwtTest3.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.apache.coyote.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon.test.jwtTest3.dto.AccountDto;
import yoon.test.jwtTest3.entity.Account;
import yoon.test.jwtTest3.entity.JwtResponse;
import yoon.test.jwtTest3.entity.Message;
import yoon.test.jwtTest3.enums.StatusEnum;
import yoon.test.jwtTest3.jwt.JwtProvider;
import yoon.test.jwtTest3.service.AccountDetailService;
import yoon.test.jwtTest3.service.AccountService;

import java.nio.charset.Charset;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final JwtProvider jwtProvider;
    private final AccountDetailService accountDetailService;
    private final AuthenticationProvider authenticationProvider;
    private final AccountService accountService;
    @GetMapping("/token")
    public ResponseEntity<Message> tokenPage(HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        String token = jwtProvider.resolveToken(request);

        message.setStatus(StatusEnum.OK);
        message.setMessage("JWT");
        message.setData(token);
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/token")
    public ResponseEntity<Message> tokenGet(AccountDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();
        String jwt = jwtProvider.createToken((Account)accountDetailService.loadUserByUsername(dto.getUsername()));

        message.setStatus(StatusEnum.OK);
        message.setMessage("JSON Web Token");
        message.setData(jwt);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }
    @PostMapping("/account/join")
    public ResponseEntity<Message> join(AccountDto dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        accountService.join(dto);

        message.setStatus(StatusEnum.OK);
        message.setMessage("Register Success");
        message.setData(dto);

        return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
    }
    @PostMapping("/account/login")
    public ResponseEntity<JwtResponse> login(AccountDto dto, HttpServletResponse res){
        try{
            Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.createToken((Account)accountDetailService.loadUserByUsername(dto.getUsername()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Account account = accountService.findAccount(userDetails.getUsername());
            res.setHeader("Authorization", "Bearer " +jwt);
            return ResponseEntity.ok().body(new JwtResponse(jwt, account.getUsername(), account.getRole()));
        }catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

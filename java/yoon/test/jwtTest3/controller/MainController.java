package yoon.test.jwtTest3.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon.test.jwtTest3.entity.Account;
import yoon.test.jwtTest3.entity.JwtResponse;
import yoon.test.jwtTest3.entity.Message;
import yoon.test.jwtTest3.enums.StatusEnum;
import yoon.test.jwtTest3.jwt.JwtProvider;
import yoon.test.jwtTest3.service.AccountService;

import javax.naming.Context;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final AccountService accountService;
    private final JwtProvider jwtProvider;
    @GetMapping("/")
    public String main(HttpServletRequest req){
        String token = req.getHeader("JWT");
        return token;
    }

    @GetMapping("/auth")
    public ResponseEntity<Message> getAuth(HttpServletRequest req){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        String jwt = req.getHeader("Authentication");
        if(jwt != null){
            Account account = accountService.findAccount(jwtProvider.getId(jwt));
            message.setStatus(StatusEnum.OK);
            message.setMessage("JWT");
            message.setData(account);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        }

        message.setStatus(StatusEnum.NOT_FOUND);
        message.setMessage("No Auth User");
        message.setData(null);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

}

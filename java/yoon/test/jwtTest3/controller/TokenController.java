package yoon.test.jwtTest3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon.test.jwtTest3.entity.Message;
import yoon.test.jwtTest3.entity.Token;
import yoon.test.jwtTest3.enums.StatusEnum;
import yoon.test.jwtTest3.jwt.JwtProvider;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @GetMapping("/token")
    public String tokenPage(){
        return "token";
    }

    @PostMapping("/token")
    public ResponseEntity<Message> tokenGet(Token token){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        String jwt = jwtProvider.createToken(token);

        message.setStatus(StatusEnum.OK);
        message.setMessage("JSON Web Token");
        message.setData(jwt);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

}

package yoon.test.jwtTest3.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yoon.test.jwtTest3.entity.Message;
import yoon.test.jwtTest3.enums.StatusEnum;

import javax.naming.Context;
import java.nio.charset.Charset;

@RestController
public class MainController {

    @GetMapping("/auth")
    public ResponseEntity<Message> getAuth(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));
        Message message = new Message();

        SecurityContext context = SecurityContextHolder.getContext();

        message.setStatus(StatusEnum.OK);
        message.setMessage("JWT");
        message.setData(context);

        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }

}

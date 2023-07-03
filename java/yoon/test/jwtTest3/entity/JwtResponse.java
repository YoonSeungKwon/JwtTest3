package yoon.test.jwtTest3.entity;

import lombok.Data;
import yoon.test.jwtTest3.enums.Role;

@Data
public class JwtResponse {

    private String token;
    private String title = "JWT Token";
    private String username;
    private Role role;

    public JwtResponse(String token, String username, Role role){
        this.token = token;
        this.username = username;
        this.role = role;

    }

}

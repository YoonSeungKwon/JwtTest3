package yoon.test.jwtTest3.enums;

public enum Role {

    GUSET("ROLE_ANONYMOUS"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String role;

    Role(String role){
        this.role = role;
    }


}

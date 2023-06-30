package yoon.test.jwtTest3.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yoon.test.jwtTest3.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class Token implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Token(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }


    //Implements


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(this.getRole())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

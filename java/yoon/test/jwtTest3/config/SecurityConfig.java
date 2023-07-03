package yoon.test.jwtTest3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import yoon.test.jwtTest3.jwt.JwtAuthenticationFIlter;
import yoon.test.jwtTest3.jwt.JwtProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountAuthenticationProvider accountAuthenticationProvider;
    private final JwtProvider jwtProvider;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/auth").hasAnyRole("USER");
                    auth.anyRequest().permitAll();
                })
                .csrf(csrf->csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtAuthenticationFIlter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(accountAuthenticationProvider)
                .build();
    }

}

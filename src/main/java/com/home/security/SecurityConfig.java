package com.home.security;

import com.home.model.repository.DebitCardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DebitCardRepository debitCardRepository;

    public SecurityConfig(DebitCardRepository debitCardRepository) {
        this.debitCardRepository = debitCardRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> login.permitAll())
                .logout(out -> out.logoutUrl("/logout").logoutSuccessUrl("/"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/register").permitAll()//.hasAnyRole("ADMIN", "OWNER")
                        .anyRequest().authenticated())
                .rememberMe(remember -> remember.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.swa.security;

import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final EmployeeRepository repository;

    public SecurityConfig(EmployeeRepository repository){
        this.repository = repository;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .csrf(Customizer.withDefaults())
                .formLogin((form) -> form
                        .loginPage("/")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/clients")
                        .failureUrl("/error-page?error=true")
                )
                .logout(LogoutConfigurer::permitAll)
                .authenticationManager(new CustomAuthenticationManager(repository));

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

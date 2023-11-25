package com.swa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter filter;

    private final CorsConfig corsConfig;

    public SecurityConfig(JwtAuthenticationFilter filter, CorsConfig corsConfig) {
        this.filter = filter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/")
                        .permitAll()
                        .requestMatchers("/api/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/api_admin/**")
                        .hasAuthority("ROLE_ADMIN")
                )
                .formLogin((form) -> form
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/clients",true)
                        .failureUrl("/error-page?error=true")
                        .permitAll()
                )
                .anonymous(AbstractHttpConfigurer::disable)
                .csrf(Customizer.withDefaults())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package com.swa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

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
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**"))
                        .hasAuthority("ROLE_USER")
                        .requestMatchers(new AntPathRequestMatcher("/api_admin/**"))
                        .hasAuthority("ROLE_ADMIN")
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((form) -> form
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/clients",true)
                        .failureUrl("/error-page?error=true")
                        .permitAll()
                )
                .httpBasic(withDefaults())
                .anonymous(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

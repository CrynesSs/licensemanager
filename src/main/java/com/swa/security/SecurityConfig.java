package com.swa.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/"))
                        .hasAuthority("ROLE_USER")
                        .requestMatchers(new AntPathRequestMatcher("/api/**"))
                        .hasAuthority("ROLE_USER")
                        .requestMatchers(new AntPathRequestMatcher("/api_admin/**"))
                        .hasAuthority("ROLE_ADMIN")
                )
                //Add JWT Filter to check token before Username and Password
                //Allow Login via Formdata from outside.
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin((form) -> form
                        .loginProcessingUrl("/authenticate")
                        .successHandler(((request, response, authentication) -> {
                            //send JWT here
                        }))
                        .successForwardUrl("/api/home")
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"success\": false, \"message\": \"" + exception.getMessage() + "\"}");
                            response.setContentType("application/json");
                        })
                        .permitAll()
                )
                .httpBasic(withDefaults())
                .anonymous(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

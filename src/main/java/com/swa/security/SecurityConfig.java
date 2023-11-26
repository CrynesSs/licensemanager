package com.swa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    public SecurityConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/"))
                        .hasAuthority("ROLE_USER")
                        .requestMatchers(new AntPathRequestMatcher("/api/**"))
                        .hasAuthority("ROLE_USER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/icon/**"),
                                new AntPathRequestMatcher("/media/**"),
                                new AntPathRequestMatcher("/manifest.json")
                        )
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api_admin/**"))
                        .hasAuthority("ROLE_ADMIN")
                )
                //Add JWT Filter to check token before Username and Password
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                //Allow Login via Formdata from outside.
                .formLogin((form) -> form
                        .loginProcessingUrl("/api/authenticate")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/error-page?error=true")
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

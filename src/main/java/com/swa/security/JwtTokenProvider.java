package com.swa.security;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final EmployeeRepository repository;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public JwtTokenProvider(EmployeeRepository repository) {
        this.repository = repository;
    }

    public String generateToken(Authentication authentication) {
        Employee userDetails = (Employee) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public Optional<Employee> validateTokenAndGetUser(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

            // Extract user ID from claims
            String userId = claimsJws.getBody().getSubject();

            // Retrieve user from the repository based on the user ID

            // Return the user if found, otherwise return null
            return repository.findById(Long.parseLong(userId));
        } catch (SignatureException e) {
            // Invalid signature, token tampered with
            return Optional.empty();
        } catch (Exception e) {
            // Other exceptions, e.g., expired token
            return Optional.empty();
        }
    }
}
package com.swa.security;

import com.swa.properSpring.user.Employee;
import com.swa.properSpring.user.EmployeeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager, AuthenticationProvider {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmployeeRepository repository;


    public CustomAuthenticationManager(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken token){
            String username = (String) token.getPrincipal();
            String password = (String) token.getCredentials();
            Employee employee = repository.findByUsername(username);
            if(employee ==null || !passwordEncoder.matches(employee.getPassword(),passwordEncoder.encode(password))){
                return null;
            }else{
                List<GrantedAuthority> authorities = new ArrayList<>(employee.getAuthorities());
                return new UsernamePasswordAuthenticationToken(employee,null,authorities);
            }
        }else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

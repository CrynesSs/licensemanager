package com.swa.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Require HTTPS for all requests
                .requiresChannel(channelRequestMatcherRegistry -> {
                    channelRequestMatcherRegistry
                            .anyRequest()
                            .requiresSecure();
                })
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(
                                    new AntPathRequestMatcher("/static/**"),
                                    new AntPathRequestMatcher("/favicon.ico"),
                                    new AntPathRequestMatcher("/**.png"),
                                    new AntPathRequestMatcher("/manifest.json"))
                            .permitAll();
                    authorize.requestMatchers(
                                    new AntPathRequestMatcher("/login"),
                                    new AntPathRequestMatcher("/"))
                            .permitAll();
                    authorize.anyRequest().authenticated();
                })
                // Enable CORS configuration
                .cors(withDefaults())
                // Disable CSRF protection for "/token" endpoint
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/token"))
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt((jwt) ->
                                jwt.decoder(jwtDecoder())
                        ))
                // Configure session management to be stateless
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure exception handling for authentication and access denial
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                // Enable HTTP basic authentication
                .httpBasic(withDefaults())
                // Permit all requests to the "/logout" endpoint
                .logout(LogoutConfigurer::permitAll);
        // Return the configured HttpSecurity as a SecurityFilterChain
        return http.build();
    }

    // Configure JWT Decoder for decoding JWT tokens
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    // Configure JWT Encoder for encoding JWT tokens
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    // Configure ServletContextInitializer for additional servlet context settings
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setSecure(true);
            servletContext.getSessionCookieConfig().setHttpOnly(true);
        };
    }


    // Configure TomcatServletWebServerFactory for additional Tomcat settings
    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.setRedirectPort(8443);
        });
        return factory;
    }

    // Configure PasswordEncoder for encoding passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

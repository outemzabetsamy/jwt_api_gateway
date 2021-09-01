package fr.univ.orleans.webservices.livedemosecurity.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

import java.util.Arrays;

import javax.crypto.SecretKey;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    JwtTokens jwtTokens;

    @Autowired
    private ServerSecurityContextRepository securityContextRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecretKey getSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Bean
    ReactiveUserDetailsService noOps() {
        return s -> Mono.empty();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http    .csrf().disable() 
                .httpBasic().disable()
                .formLogin().disable()
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .pathMatchers(HttpMethod.GET,"/api/messages/subscribe").hasRole("USER")
                .pathMatchers(HttpMethod.GET,"/api/messages/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/utilisateurs").hasRole("ADMIN")
                .pathMatchers(HttpMethod.POST,"/auth/register").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/hotel/add").permitAll()
                .pathMatchers(HttpMethod.POST,"/image/upload").permitAll()
                .anyExchange().authenticated();
        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
       // corsConfig.addAllowedHeader("Authorization");
       // corsConfig.addAllowedHeader("Access-Control-Allow-Origin:*");
        //corsConfig.setAllowedHeaders(Arrays.asList("Authorization","Content-Length","Access-Control-Request-Headers","Access-Control-Allow-Origin:*"));
        corsConfig.setExposedHeaders(Arrays.asList("Authorization","Content-Length"));
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfig.setAllowedOrigins(Arrays.asList("*","http://localhost:8080/login"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}

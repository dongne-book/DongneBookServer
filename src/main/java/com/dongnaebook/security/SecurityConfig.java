package com.dongnaebook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                            corsConfig.setAllowCredentials(true);
                            corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:3000"));
                            corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE","PATCH"));
                            corsConfig.setAllowedHeaders(java.util.List.of("*"));
                            return corsConfig;
                        }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/signup", "/api/auth/login","/oauth/kakao/callback","/oauth/google/callback")
                        .permitAll()// 회원가입, 로그인은 인증 없이해야함
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic->httpBasic.disable())
                .formLogin(formLogin->formLogin.disable())
         .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

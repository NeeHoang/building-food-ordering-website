package com.food.configurations;

import com.food.security.JwtAuthenticationEntryPoint;
import com.food.security.JwtFilter;
import com.food.services.JwtService;
import com.food.services.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Getter

public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtService jwtService;
    private final IUserService userService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**", "/product/**").hasRole("admin")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/oauth2-login")
                        .successHandler((request, response, authentication) -> {
                            var oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                            var email = oidcUser.getEmail();
                            var name = oidcUser.getName();
                            var user = userService.saveOrUpdateGoogleUser(email, name);
                            var jwt = jwtService.generateToken(
                                    String.valueOf(user.getId()),
                                    user.getEmail(),
                                    Collections.singletonList(user.getRole())
                            );
                            response.sendRedirect("/auth/google-callback?token=" + jwt);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            String json = String.format("{\"error\": \"Google login failed\", \"message\": \"%s\"}", exception.getMessage());
                            response.getWriter().write(json);
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}




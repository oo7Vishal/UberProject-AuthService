package com.vishal.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for development (enable it in production!)
                .csrf(csrf -> csrf.disable())

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers("/api/v1/auth/signup/*", "/api/auth/login", "/public/**").permitAll()

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Form login (useful for testing in browser)
                .formLogin(form -> form
                        .loginPage("/login")           // custom login page if you have one
                        .permitAll()
                        .defaultSuccessUrl("/home", true)
                )

                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

        // Optional: HTTP Basic authentication (good for testing APIs)
        // .httpBasic(httpBasic -> httpBasic.realmName("Uber Auth Service"))

        // If you later add JWT / stateless auth, disable session creation here
        // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        return http.build();
    }
}
package com.example.studentreg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // disable CSRF for now
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/admin/**").authenticated()
                                                .anyRequest().permitAll())
                                .formLogin(form -> form
                                                .loginPage("/admin/login").permitAll()
                                                .defaultSuccessUrl("/admin/dashboard", true))
                                .logout(logout -> logout.permitAll());

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService(PasswordEncoder encoder) {
                InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();

                mgr.createUser(User.withUsername("admin")
                                .password(encoder.encode("adminpass"))
                                .roles("ADMIN")
                                .build());

                mgr.createUser(User.withUsername("student")
                                .password(encoder.encode("studentpass"))
                                .roles("STUDENT")
                                .build());

                return mgr;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

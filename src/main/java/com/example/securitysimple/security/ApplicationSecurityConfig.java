package com.example.securitysimple.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.securitysimple.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.securitysimple.security.ApplicationUserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    public static final String MANAGEMENT_API = "/management/api/**";

    // adding basic auth without possibility to logout (every request server need user:password)
    // WebSecurityConfigureAdapter is deprecated, so we use filterChain in place of Configure Methode
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() 
                .authorizeHttpRequests(authz ->
                        // whitelist
                        authz.antMatchers("/", "index", "/css", "/js/*")
                                .permitAll()
                                .antMatchers("/api/**").hasAnyRole(STUDENT.name())
                                .antMatchers(HttpMethod.GET,MANAGEMENT_API).hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                                .antMatchers(HttpMethod.POST,MANAGEMENT_API).hasAuthority(COURSE_WRITE.getPermission())
                                .antMatchers(HttpMethod.PUT,MANAGEMENT_API).hasAuthority(COURSE_WRITE.getPermission())
                                .antMatchers(HttpMethod.DELETE, MANAGEMENT_API).hasAuthority(COURSE_WRITE.getPermission())
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    // Add user
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        //.roles(ADMIN.name())
                        .authorities(ADMIN.getGrantedAuthorities())
                        .build(),
                User.builder()
                        .username("fayçal")
                        .password(passwordEncoder().encode("password"))
                        //.roles(STUDENT.name())
                        .authorities(STUDENT.getGrantedAuthorities())
                        .build(),
                User.builder()
                        .username("imene")
                        .password(passwordEncoder().encode("password123"))
                       // .roles(ADMINTRAINNER.name())
                        .authorities(ADMINTRAINEE.getGrantedAuthorities())
                        .build()
        );
    }

    // define password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

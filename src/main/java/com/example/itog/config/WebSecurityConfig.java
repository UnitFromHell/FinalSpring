package com.example.itog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private  CustomUserDetailsService userService;

    public WebSecurityConfig(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public AuthenticationManager authenticationManager

    (HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests .requestMatchers("/", "/registration").permitAll()
                        .requestMatchers("/people/**").hasRole("ADMIN")
                        .requestMatchers("/product/edit/**").hasRole("EMPLOYEE")
                        .requestMatchers("/product/new").hasRole("EMPLOYEE")
                        .requestMatchers("/product/delete/**").hasRole("EMPLOYEE")
                        .requestMatchers("/type").hasRole("EMPLOYEE")
                        .requestMatchers("/category").hasRole("EMPLOYEE")
                        .requestMatchers("/size").hasRole("EMPLOYEE")
                        .requestMatchers("/cklad").hasRole("ADMIN")
                        .requestMatchers("/order").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ) .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/product")
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
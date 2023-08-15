package com.example.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String FETCH_USERS_QUERY = """
            select id, password, enabled\s
            from users\s
            where id = ?;""";

    private static final String FETCH_ROLES_QUERY = """
            select user_email, role\s
            from roles\s
            where user_email = ?;""";


    private final DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(FETCH_USERS_QUERY)
                .authoritiesByUsernameQuery(FETCH_ROLES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login")
                        .permitAll())

                .logout(logout -> logout
                        .logoutRequestMatcher((new AntPathRequestMatcher("/logout")))
                        .permitAll())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AntPathRequestMatcher.antMatcher("users/profile")).fullyAuthenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/resume/applicant/**")).hasAuthority("APPLICANT")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/resume/**")).fullyAuthenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/vacancy/employer/**")).hasAuthority("EMPLOYER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/jobs/apply")).hasAuthority("APPLICANT")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/jobs/**")).fullyAuthenticated()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}

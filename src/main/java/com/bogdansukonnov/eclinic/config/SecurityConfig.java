package com.bogdansukonnov.eclinic.config;

import com.bogdansukonnov.eclinic.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.bogdansukonnov.eclinic.security.AuthorityType.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/login/*", "/resource/**")
                    .permitAll()
                .and().authorizeRequests()
                    .antMatchers("/doctor/*")
                    .hasRole(ROLE_DOCTOR.toString())
                .and().authorizeRequests()
                    .antMatchers("/admin/*")
                    .hasRole(ROLE_ADMIN.toString())
                .and().authorizeRequests()
                    .antMatchers("/nurse/*")
                    .hasRole(ROLE_NURSE.toString())
                .and().formLogin()
                    .loginPage("/login/sign-in")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .permitAll()
                .and().authorizeRequests()
                    .anyRequest()
                    .permitAll()
                .and()
                    .csrf().disable();
    }

}
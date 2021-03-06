package com.bogdansukonnov.eclinic.config;

import com.bogdansukonnov.eclinic.dao.UserDao;
import com.bogdansukonnov.eclinic.service.AppUserDetailsServiceImpl;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDao userDao;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsServiceImpl(userDao);
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests().antMatchers("/", "/login/**", "/static/**", "/error/**", "/info/**")
                .permitAll()

                .and().formLogin()
                .loginPage("/login/sign-in")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .loginProcessingUrl("/login/doLogin")
                .successForwardUrl("/login/postLogin")
                .failureUrl("/login/loginFailed")
                .and()
                .logout()
                .logoutUrl("/login/sign-out")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll()

                .and().authorizeRequests().antMatchers("/doctor/**")
                .hasAnyRole(ROLE_DOCTOR.getRole(), ROLE_ADMIN.getRole())

                .and().authorizeRequests().antMatchers("/admin/**")
                .hasAnyRole(ROLE_ADMIN.getRole())

                .and().authorizeRequests().antMatchers("/nurse/**")
                .hasAnyRole(ROLE_NURSE.getRole(), ROLE_ADMIN.getRole(), ROLE_DOCTOR.getRole())

                .and()
                .exceptionHandling().accessDeniedPage("/error/access-denied")
        ;

    }

}
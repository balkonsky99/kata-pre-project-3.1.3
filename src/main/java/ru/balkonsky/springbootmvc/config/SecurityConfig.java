package ru.balkonsky.springbootmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.balkonsky.springbootmvc.config.handler.LoginSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final LoginSuccessHandler loginSuccessHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}

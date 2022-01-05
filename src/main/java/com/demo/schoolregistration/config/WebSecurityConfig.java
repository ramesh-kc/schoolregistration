package com.demo.schoolregistration.config;

import com.demo.schoolregistration.config.security.AuthenticationTokenFilter;
import com.demo.schoolregistration.config.security.UnauthorizedEntryPoint;
import com.demo.schoolregistration.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userDetailsService;
  private final UnauthorizedEntryPoint unauthorizedEntryPoint;
  private final AuthenticationTokenFilter authenticationTokenFilter;

  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, UnauthorizedEntryPoint unauthorizedEntryPoint, AuthenticationTokenFilter authenticationTokenFilter) {
    this.userDetailsService = userDetailsService;
    this.unauthorizedEntryPoint = unauthorizedEntryPoint;
    this.authenticationTokenFilter = authenticationTokenFilter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
    authBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/auth/**")
        .permitAll()
        .antMatchers("/api/test/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    httpSecurity.addFilterBefore(
        authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}

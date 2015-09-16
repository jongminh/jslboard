package com.jslss.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jslss.board.repository.UserDetailsServiceImpl;
import com.jslss.board.repository.UserRepository;


@Configuration
@EnableWebMvcSecurity
@ComponentScan("com.jslss.board")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	UserRepository  userRepository;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
		        .antMatchers("/css/**").permitAll()
				.anyRequest().permitAll()
				.and()
	        .authorizeRequests()
                .antMatchers("/register").permitAll()
                .anyRequest().anonymous()
                .and()
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin().usernameParameter("email").passwordParameter("password")
                .loginPage("/login").failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.
    		userDetailsService(new UserDetailsServiceImpl(userRepository)).passwordEncoder(new BCryptPasswordEncoder());
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    }
}
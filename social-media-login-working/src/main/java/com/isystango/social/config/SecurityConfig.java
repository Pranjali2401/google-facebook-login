package com.isystango.social.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

//		Google config
//		http.authorizeRequests()
//		.antMatchers("/", "/user/", "/user/login", "/user/registration").permitAll()
//		.anyRequest()
//		.authenticated()
//		.and().oauth2Login();
			
//		Google config
		http.authorizeRequests()
		.antMatchers("/", "/user/", "/user/login", "/user/registration").permitAll()
		.anyRequest()
		.authenticated()
		.and().oauth2Login()
//		.and().logout().logoutSuccessUrl("/user/").permitAll();
		.and().logout().logoutUrl("/logout").invalidateHttpSession(true)
        .clearAuthentication(true).logoutSuccessUrl("/user/").deleteCookies("JSESSIONID").permitAll().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		
		
//		facebook config
//		http
//		.authorizeRequests()
//		.antMatchers("/", "/user/", "/user/login", "/user/registration").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin().permitAll();
		
		

//		http.authorizeRequests()
//		.antMatchers("/user/").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin().permitAll();
//		.and()
//		.oauth2Login()
//		.loginPage("/user/signin/facebook");
//		and().oauth2Login().loginPage("/user/signin/facebook");
		
		
	}

}

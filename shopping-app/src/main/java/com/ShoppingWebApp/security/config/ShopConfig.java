package com.ShoppingWebApp.security.config;

import java.util.List;

import org.aspectj.weaver.tools.Trace;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ShoppingWebApp.security.ShopUserDetailService;
import com.ShoppingWebApp.security.jwt.JwtAuthEntryPoint;
import com.ShoppingWebApp.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {

	@Autowired
	private ShopUserDetailService detailService;
	
	@Autowired
	private JwtFilter filter;
	
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;
	
	private static final List<String> SECURED_URLS=
							List.of("/api/v1/carts/**","/api/vi/itemcart/**","/api/vi/orders/**");
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated();
                    requests.anyRequest().permitAll();}
                )
                .exceptionHandling(exception->exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(management -> 
                	management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                
        			
		
		 return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}

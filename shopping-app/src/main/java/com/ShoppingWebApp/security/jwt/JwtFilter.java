package com.ShoppingWebApp.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ShoppingWebApp.security.ShopUserDetailService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private ShopUserDetailService shopUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader= request.getHeader("Authorization");
		String username=null;
		String jwt=null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			jwt=authHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
		}
		if(username!=null) {
			UserDetails userDetails = shopUserDetailService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt)) {
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		
		}
		
		filterChain.doFilter(request, response);
	}

}

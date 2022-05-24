package com.demo.usersec.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.usersec.service.impl.JwtUserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtTokenAuthFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenAuthFilter.class);
	
	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.debug("Authentication Request For '{}'", request.getRequestURL());
        LOGGER.debug("entire request : {}" , request);

        final String requestTokenHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                LOGGER.error("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
            } catch (ExpiredJwtException e) {
                LOGGER.warn("JWT_TOKEN_EXPIRED", e);
            }
        } else {
            LOGGER.warn("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING; Maybe first time authentication");
        }

        LOGGER.debug("Before: JWT_TOKEN_USERNAME_VALUE '{} , {}'", username , SecurityContextHolder.getContext().getAuthentication());
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsServiceImpl.loadUserByUsername(username);
            LOGGER.debug("Filter {}", username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails).equals(Boolean.TRUE)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        else {
        	LOGGER.debug("Else filter" );
        }
        chain.doFilter(request, response);
    }

}
	


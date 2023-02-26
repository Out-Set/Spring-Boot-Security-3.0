package com.example.demosecurity.Filter;

import com.example.demosecurity.Config.CustomUserDetailsService;
import com.example.demosecurity.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // We have request and response, from the request get the Authorization Header then extract the username, password..etc, then validate.

        // 1. Get the Header
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;

        // Getting JWT token and userName from request
        if(authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);

            userName = jwtService.extractUserName(token);

            // With this user-name, go to the user-detail-service, pass this user-name and load the user-detail subject
            // then validate the user-detail subject which i have and passed to the jwt-service.
        }

        // Validate Token and Set Spring Security
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if(jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

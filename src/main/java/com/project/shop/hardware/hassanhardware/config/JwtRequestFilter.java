package com.project.shop.hardware.hassanhardware.config;
/*import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;*/

//@Component
public class JwtRequestFilter { //extends OncePerRequestFilter {

    /*@Value("${jwt.header}")
    private String jwtHeader;

    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(jwtHeader);

        String username = null;
        String jwtToken = null;

        // Check if the request has a JWT token in the header
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7); // Remove "Bearer " prefix
        }

        // Once we have the JWT token, validate it and set up authentication if valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        }

        chain.doFilter(request, response);
    }*/
}

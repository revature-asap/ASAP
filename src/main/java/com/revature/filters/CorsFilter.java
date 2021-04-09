package com.revature.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter("/*")
public class CorsFilter extends OncePerRequestFilter {

    /**
     * Sets the CORS filter for incoming HTTP requests and outgoing responses
     * Allows access from all urls (We couldn't figure out how to limit it to just localhost and our frontend)
     * The header for the JWT is called {@code ASAP-token}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","Content-Type, ASAP-token");
        httpServletResponse.setHeader("Access-Control-Expose-Headers","ASAP-token");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

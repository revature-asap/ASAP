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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        /*
            urls available:
                - localhost (for local testing)
                - p3-210119-java-enterprise.s3.amazonaws.com (I expect this to be the url for our front end)
            header for JWT:
                - ASAP-token
        */
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // httpServletResponse.setHeader("Access-Control-Allow-Origin","http://localhost:4200, https://p3-210119-java-enterprise.s3.amazonaws.com");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","Content-Type, ASAP-token");
        httpServletResponse.setHeader("Access-Control-Expose-Headers","ASAP-token");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

package com.revature.aspects;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


import com.revature.annotations.Secured;
import com.revature.dtos.Principal;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.util.JwtParser;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
public class SecurityAspect {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private final JwtParser jwtParser;

    @Autowired
    public SecurityAspect(HttpServletRequest request, HttpServletResponse response, JwtParser jwtParser) {
        this.request = request;
        this.response = response;
        this.jwtParser = jwtParser;
    }


    @Around("@annotation(com.revature.annotations.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured securedAnno = method.getAnnotation(Secured.class);

        List<String> allowedRoles = Arrays.asList(securedAnno.allowedRoles());

        String token = jwtParser.getTokenFromHeader(request);
        Principal principal = jwtParser.parseToken(token);
        try {


            if (principal == null) {
                response.setStatus(401);
                throw new AuthenticationException();
            }

            if (!allowedRoles.contains(principal.getRole().toString())) {
                response.setStatus(403);
                throw new AuthorizationException();
            }
            response.setStatus(200);
            return pjp.proceed();

        } catch (AuthenticationException | AuthorizationException e) {}

        return null;


    }
}
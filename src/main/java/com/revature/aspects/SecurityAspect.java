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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Aspect for all methods marked with @Secured
 * Allows or Denies access to endpoints based on role supplied by the client
 * if no role is supplied, access to the endpoint is denied.
 * Allowed roles are set with @Secured(allowRoles={"allowd_role1", "allowed_role2"})
 */
@Component
@Aspect
public class SecurityAspect {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private final JwtParser jwtParser;

    /**
     * Constructor that autowires the Web Token Parser, the request and response
     * @param request
     * @param response
     * @param jwtParser
     */
    @Autowired
    public SecurityAspect(HttpServletRequest request, HttpServletResponse response, JwtParser jwtParser) {
        this.request = request;
        this.response = response;
        this.jwtParser = jwtParser;
    }


    /**
     * The code that gets executed when aspect is called.
     * Parses the token to get user data (if user data exists)
     * then checks whether or not user has appropriate permissions to access the endpoint
     * sets a response code indicating OK, forbidden, or unauthorized
     * @param pjp the join point
     * @return .proceed() allowing the method to mover foreward
     * @throws Throwable
     */
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

package com.revature.aspects;


import com.revature.annotations.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


@Component
@Aspect
public class SecurityAspect {

    private HttpServletRequest request;
    private final JwtParser jwtParser;

    @Autowired
    public SecurityAspect(HttpServletRequest request, JwtParser jwtParser) {
        this.request = request;
        this.jwtParser = jwtParser;
    }
    

    @Around("@annotation(com.revature.annotations.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured securedAnno = method.getAnnotation(Secured.class);

        List<String> allowedRoles = Arrays.asList(securedAnno.allowedRoles());

        Cookie[] cookies = request.getCookies();

        if (cookies == null ) {
            throw new AuthenticationException("An unauthenticated request was made to a protected endpoint!");
        }

        String token = Stream.of(cookies)
                .filter(c -> c.getName().equals("bb-token"))
                .findFirst()
                .orElseThrow(AuthenticationException::new)
                .getValue();

        Principal principal = jwtParser.parseToken(token);

        //Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            throw new AuthenticationException();
        }
        if (!allowedRoles.contains(principal.getRole().toString())) {
            System.out.println(principal.getRole());
            throw new AuthorizationException();
        }

        return pjp.proceed();

    }


}

package com.revature.util;

import com.revature.dtos.Principal;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public class Jwtparser {

    private JwtConfig jwtConfig;

    @Autowired
    public Jwtparser(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public Principal parseToken(String token){

        Claims claims = Jwts.parser()
                        .setSigningKey(jwtConfig.getSigningKey())
                        .parseClaimsJws(token)
                        .getBody();

        int id = Integer.parseInt(claims.getId());
        String username = claims.getSubject();
        String role = (String) claims.get("role");
        UserRole userRole = UserRole.valueOf(role);
        return new Principal(id,username,userRole);


    }

    public String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("ASAP-token");

        if(token == null || token.isEmpty()){
            throw new InvalidRequestException("Not Logged In");
        }

        return token;
    }



}

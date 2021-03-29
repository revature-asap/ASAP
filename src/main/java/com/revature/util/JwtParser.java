package com.revature.util;

import com.revature.dtos.Principal;
import com.revature.entities.UserRole;
import com.revature.exceptions.InvalidRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * The class is responsible for parsing tokens and returns a token from the request header.
 */
@Component
public class JwtParser {

    private JwtConfig jwtConfig;

    /**
     * Constructor for auto wiring JWT config.
     * @param jwtConfig is the JWT configuration class
     */
    @Autowired
    public JwtParser(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    /**
     * Takes in the string token and parse the token by going through the JWTs parser.
     * The parser returns a claim and using the claim to get all the principle data and set it to a new principle.
     * Last, return the principle object that contains the id, username, and role of the user.
     * @param token is the JWT token
     * @return the principle of the given token
     */
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

    /**
     * Takes in a Http Servlet Request. Store the ASAP token in a new Token off the request.
     * If the token is null or empty, throw Invalid request exception. Else, return the string Token.
     * @param request the Http Servlet Request
     * @return the ASAP token from the request header
     */
    public String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("ASAP-token");

        if(token == null || token.isEmpty()){
            throw new InvalidRequestException("Not Logged In");
        }

        return token;
    }



}

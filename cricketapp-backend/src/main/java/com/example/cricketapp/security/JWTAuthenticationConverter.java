package com.example.cricketapp.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class JWTAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if(header==null || !(header.startsWith("Bearer")))
            return null;
        
        var jwt = header.replace("Bearer ", "");

        return new JWTAuthentication(jwt);
    }
}

package com.example.cricketapp.security;

import com.example.cricketapp.Service.AdminService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationManager implements AuthenticationManager {

    private final JWTService jwtService;
    private final AdminService adminService;

    public JWTAuthenticationManager(JWTService jwtService, AdminService usersService) {
        this.jwtService = jwtService;
        this.adminService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub

        if(authentication instanceof JWTAuthentication jwtAuthentication)
        {
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);
            jwtAuthentication.admin = adminService.getAdmin(userId);
            jwtAuthentication.setAuthenticated(true);
            return jwtAuthentication;
        }

        throw new IllegalAccessError("Cannot authenticate non-JWT authentication");
    }
}

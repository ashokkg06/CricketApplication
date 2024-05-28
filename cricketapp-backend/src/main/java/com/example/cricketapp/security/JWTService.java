package com.example.cricketapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    // TODO: MOVE jwt token to .properties file
    private final Algorithm algorithm;

    public JWTService(@Value("${jwt.secret.key}") String jwtKey) {
        this.algorithm = Algorithm.HMAC256(jwtKey);
    }

    public String createJwt(int userId) {
        System.out.println("Creating jwt token...");
        return JWT.create()
                        .withSubject(Integer.toString(userId))
                        .withIssuedAt(new Date())
                        // .withExpiresAt(null) //Handle expiry
                        .sign(algorithm);
    }

    public int retrieveUserId(String jwtString) {
        var decodedJwt = JWT.decode(jwtString);
        return Integer.parseInt(decodedJwt.getSubject());
        // try {
        //     String decodedJwt = JWT.decode(jwtString).toString();
        //     Long userId = Long.valueOf(decodedJwt);
        //     return userId;
        // } catch (JWTDecodeException e) {
        //     throw new Illegal
        // } catch (NumberFormatException e) {

        // }
    }

}

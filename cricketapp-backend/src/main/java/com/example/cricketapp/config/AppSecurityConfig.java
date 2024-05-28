package com.example.cricketapp.config;

import com.example.cricketapp.Service.AdminService;
import com.example.cricketapp.security.JWTAuthenticationFilter;
import com.example.cricketapp.security.JWTAuthenticationManager;
import com.example.cricketapp.security.JWTService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JWTService jwtService;

    private final AdminService adminService;

    public AppSecurityConfig(JWTService jwtService, AdminService adminService) {
        this.jwtService = jwtService;
        this.adminService = adminService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, adminService)
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        try {
            http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorize -> authorize
                            //                        .requestMatchers(
                            //                                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/iplseries"),
                            //                                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/iplseries/**"),
                            //                                AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/admin/register/"),
                            //                                AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/admin/register/**"))
                            //                        .permitAll()
                            //                        .anyRequest().authenticated()
                            .requestMatchers(
                                    AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/admin-panel"))
                            .authenticated()
                            .anyRequest().permitAll()
                    )
                    //                 .formLogin((formLogin) -> formLogin
                    //                     .loginPage("/admin/login")
                    //                     .permitAll()
                    //                 )
                    // .headers().frameOptions().sameOrigin()
                    .headers(headers -> headers
                            .frameOptions(frameOptions -> frameOptions
                                    .sameOrigin()))
                    .rememberMe(Customizer.withDefaults());

            http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
            Logger logger = LoggerConfig.getLogger();
            logger.error(e.toString());
        }
        return null;
    }
}

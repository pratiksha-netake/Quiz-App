package com.QuizApp.QuizApp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request ->{
            	CorsConfiguration config =new CorsConfiguration();
            	config.addAllowedOrigin("http://localhost:8080");
            	config.addAllowedMethod("*");
            	config.addAllowedHeader("*");
            	config.setAllowCredentials(true);
            	return config;
            }))

            .authorizeHttpRequests(auth -> auth
            		
            		.requestMatchers(
            				"/",
            				"/register.html",
            				"/user.html",
            				 "/*.html",
            				"/login.html",
            				"/admin.html",
            				 "/createquiz.html",   
            	                "/updatequiz.html",
            	                "/updatequiz1.html",
            	                "/deletequiz.html",
            				"/css/**"
            				).permitAll()
            		
            	.requestMatchers("/favicon.ico").permitAll()

              
                .requestMatchers("/auth/**").permitAll()

               
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            
           
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
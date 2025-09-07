package com.example.spring_boot_rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Now this will disable the default security configuration done by spring, now this class will be used to provide security filters to the web app
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    //Way to use login credentials fetched from Database. Spring uses a default authentication provider layer, which is now custom implemented.
    @Bean
    public AuthenticationProvider authProvider(){
        //AuthenticationProvider is used by spring bydefault. The DaoAuthentication Class helps in connecting with Database for security
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
//      provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  //If you storing password normally, then no encoding is required
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));   //To use password encoding for authentication of a user (because user will enter normal password that will be encoded and matched in the database while retrieving the data)
        return provider;
    }

    //Implementing custom Security filter chain, this disables the default security chain used by spring.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Just chaining the methods
        http
                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())   // Require authentication for all requests
                .authorizeHttpRequests(request-> request
                        .requestMatchers("/register")   //This will allow this endpoint to be accessed without authentication
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

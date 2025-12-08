package com.premierLeague.premier_Zone.config;

import com.premierLeague.premier_Zone.security.CustomUserDetailsService;
import com.premierLeague.premier_Zone.security.JwtAuthFilter;
import com.premierLeague.premier_Zone.security.OAuth2AuthenticationSuccessHandler;
import com.premierLeague.premier_Zone.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
   @Autowired
   private JwtAuthFilter jwtAuthFilter;

   @Autowired
   private CustomUserDetailsService userDetailsService;

   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private CustomOAuth2UserService customOAuth2UserService;
   @Autowired
   private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
       return config.getAuthenticationManager();
   }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
    http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->auth
                    .requestMatchers("/auth/login","/auth/register","/error","/players/**","/oauth2/**","/login/**","/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html").permitAll()
                    .requestMatchers("user/all-user", "user/create-admin").hasAnyRole("ADMIN", "ADMIN-BY-GOOGLE")
                    .anyRequest().authenticated())
            .oauth2Login(oauth-> oauth
                    .authorizationEndpoint(a->a.baseUri("/oauth2/authorization"))
                    .redirectionEndpoint(r->r.baseUri("/login/oauth2/code/*"))
                    .userInfoEndpoint(info->info.oidcUserService(customOAuth2UserService))
                    .successHandler(oAuth2AuthenticationSuccessHandler)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();

   }
}

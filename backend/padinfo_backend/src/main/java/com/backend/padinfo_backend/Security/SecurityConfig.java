package com.backend.padinfo_backend.Security;

import com.backend.padinfo_backend.exceptions.userInfo.UserInfoNotFoundException;
import com.backend.padinfo_backend.model.enums.ERole;
import com.backend.padinfo_backend.model.service.UserInfo.IUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userInfoService.findByUsername(username).orElseThrow(
                () -> new UserInfoNotFoundException("User not found")
        );
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/checkUser").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/userInfoByName").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/updateIsConnected/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/isConnected/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/tournaments").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name(), ERole.GUEST.name())
                                .requestMatchers(HttpMethod.GET, "/api/players").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name(), ERole.GUEST.name())
                                .requestMatchers("/api/games/**").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name())
                                .requestMatchers("/api/games").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name())
                                .requestMatchers("/api/users/**").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name())
                                .requestMatchers("/api/users").hasAnyRole(ERole.ADMIN.name(), ERole.USER.name())
                                .requestMatchers("/api/players/**").hasRole(ERole.ADMIN.name())
                                .requestMatchers("/api/tournaments/**").hasRole(ERole.ADMIN.name())
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}

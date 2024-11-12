package com.sportus.be.global.config;

import com.sportus.be.auth.application.CustomOAuth2UserService;
import com.sportus.be.auth.handler.OAuth2LoginFailureHandler;
import com.sportus.be.auth.handler.OAuth2LoginSuccessHandler;
import com.sportus.be.auth.presentation.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] WHITE_LIST = {
            "/error",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/*",
            "/webjars/**",
            "/auth/**",
            "/global/health-check",
            "/actuator/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP 기본 인증을 비활성화
                .cors(Customizer.withDefaults()) // CORS 활성화 - corsConfigurationSource 이름의 빈 사용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth // 요청에 대한 인증 설정
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())  //이외의 요청은 전부 인증 필요
                .oauth2Login(oauth ->
                        oauth.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
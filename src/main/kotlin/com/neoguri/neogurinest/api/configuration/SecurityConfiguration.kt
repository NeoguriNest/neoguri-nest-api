package com.neoguri.neogurinest.api.configuration;

import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Slf4j
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeRequests()
            .mvcMatchers("/", "/health-check", "/error", "/api/auth/login", "/api/users/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .disable()
            .csrf()
            .disable()
            .headers()
            .disable()
            .httpBasic()
            .disable()
            .rememberMe()
            .disable()
            .logout()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .exceptionHandling()
//            .accessDeniedHandler(accessDeniedHandler())
//            .authenticationEntryPoint(authenticationEntryPoint())
//            .and()
//            .addFilterBefore(jwtAuthenticationFilter(jwt, tokenService), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }


}

package com.neoguri.neogurinest.api.configuration.security;

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Slf4j
@EnableWebSecurity
@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests {
                it.mvcMatchers("/", "/health-check", "/error", "/api/auth/login", "/api/users/register").permitAll()
                .anyRequest().authenticated()
            }
            .formLogin {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .headers {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .rememberMe {
                it.disable()
            }
            .logout {
                it.disable()
            }
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

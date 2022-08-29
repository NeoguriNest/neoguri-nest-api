package com.neoguri.neogurinest.api.configuration.security;

import com.neoguri.neogurinest.api.configuration.security.entrypoint.NeoguriAuthenticationEntryPoint
import com.neoguri.neogurinest.api.configuration.security.filter.AccessTokenAuthenticationFilter
import com.neoguri.neogurinest.api.configuration.security.filter.AnonymousAuthenticationFilter
import com.neoguri.neogurinest.api.configuration.security.filter.RequireLoginFilter
import com.neoguri.neogurinest.api.configuration.security.provider.AccessTokenAuthenticationProvider
import com.neoguri.neogurinest.api.configuration.security.provider.AnonymousAuthenticationProvider
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import javax.annotation.Resource


@Slf4j
@EnableWebSecurity
@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityConfiguration(
    val accessTokenAuthenticationProvider: AccessTokenAuthenticationProvider?,
    val anonymousAuthenticationProvider: AnonymousAuthenticationProvider?
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer {
            it.ignoring().mvcMatchers(
                "/",
                "/health-check",
                "/error",
                "/api/auth/login",
                "/api/auth/refresh",
                "/api/users/register"
            )
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity?
    ): SecurityFilterChain {
        http!!.authorizeRequests {
                it.anyRequest().authenticated()
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
            .and()
            .exceptionHandling {
                it.authenticationEntryPoint(neoguriAuthenticationEntryPoint())
            }

        http.apply(AccessTokenAuthenticationFilter.AccessTokenAuthenticationFilterConfigurer.getBean())
        http.apply(RequireLoginFilter.RequireLoginFilterConfigurer.getBean())
        http.apply(AnonymousAuthenticationFilter.AnonymousAuthenticationFilterConfigurer.getBean())

        return http.build()
    }

    @Resource
    fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(accessTokenAuthenticationProvider)
        auth?.authenticationProvider(anonymousAuthenticationProvider)
    }

    @Bean
    fun neoguriAuthenticationEntryPoint(): NeoguriAuthenticationEntryPoint {
        return NeoguriAuthenticationEntryPoint()
    }

}

package io.jongyun.graphinstagram.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun defaultPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun configurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedHeader("*")
        configuration.addAllowedOriginPattern("*")
        configuration.addAllowedMethod("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configure(http: HttpSecurity?) {
        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http?.logout()?.disable()?.formLogin()?.disable()?.httpBasic()?.disable()

        http?.cors()?.configurationSource(configurationSource())
            ?.and()?.csrf()?.disable()?.authorizeRequests()?.anyRequest()?.permitAll()
    }
}
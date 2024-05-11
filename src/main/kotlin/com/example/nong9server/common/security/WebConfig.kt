package com.example.nong9server.common.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//
//import org.springframework.context.annotation.Bean
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.DefaultSecurityFilterChain


//@EnableWebSecurity
//class SecurityConfig {
//    @Bean
//    fun configure(http: HttpSecurity): DefaultSecurityFilterChain =
//        run {
//            http.csrf {
//
//            }
//            http.build()
//        }

//}

@Configuration
class WebConfig(
    private val jwtSessionArgumentResolver: JwtSessionArgumentResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtSessionArgumentResolver)
    }
}

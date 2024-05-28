package com.example.nong9server.common.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//@EnableWebSecurity
//class SecurityConfig {
//    @Bean
//    fun configure(http: HttpSecurity): DefaultSecurityFilterChain =
//        run {
//            http.csrf {
//                it.disable()
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

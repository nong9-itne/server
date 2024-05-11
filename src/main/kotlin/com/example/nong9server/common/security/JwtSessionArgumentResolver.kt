package com.example.nong9server.common.security

import com.example.nong9server.app.member.infrastructure.repository.MemberRepository
import com.example.nong9server.common.exception.UnidentifiedMemberException
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtSessionArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(MemberClaim::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION) ?: throw UnidentifiedMemberException()

        val token = HeaderHandler.extractBearerToken(authorization)

        val memberId = jwtTokenProvider.getSubject(token)

        return memberRepository.findMemberByMemberId(memberId)
    }
}

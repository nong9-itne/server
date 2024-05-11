package com.example.nong9server.app.member.application

import com.example.nong9server.app.member.dto.GenerateTokenRequest
import com.example.nong9server.app.member.dto.TokenResponse
import com.example.nong9server.app.member.infrastructure.repository.MemberRepository
import com.example.nong9server.common.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val tokenProvider: JwtTokenProvider,
) {

    fun generateTokenWithLogin(generateTokenRequest: GenerateTokenRequest): TokenResponse {
        val member = memberRepository.findMemberByMemberId(generateTokenRequest.memberId)

        member.authenticate(generateTokenRequest.password)

        val token = tokenProvider.createToken(generateTokenRequest.memberId)

        return TokenResponse(token)
    }

}

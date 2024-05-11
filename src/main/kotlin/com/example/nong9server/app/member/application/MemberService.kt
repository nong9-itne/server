package com.example.nong9server.app.member.application

import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.GenerateTokenWithLoginRequest
import com.example.nong9server.app.member.dto.GenerateTokenWithRegisterRequest
import com.example.nong9server.app.member.dto.MemberInfoResponse
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

    fun generateTokenWithLogin(generateTokenWithLoginRequest: GenerateTokenWithLoginRequest): TokenResponse {
        val member = memberRepository.findMemberByMemberId(generateTokenWithLoginRequest.memberId)

        member.authenticate(generateTokenWithLoginRequest.password)

        val token = tokenProvider.createToken(generateTokenWithLoginRequest.memberId)

        return TokenResponse(token)
    }

    fun generateTokenWithRegister(generateTokenWithRegisterRequest: GenerateTokenWithRegisterRequest): TokenResponse {
        val member = Member(
            memberId = generateTokenWithRegisterRequest.memberId,
            password = generateTokenWithRegisterRequest.password,
            memberName = generateTokenWithRegisterRequest.memberName,
        )

        memberRepository.registerMember(member)

        val token = tokenProvider.createToken(member.memberId)

        return TokenResponse(token)
    }
}

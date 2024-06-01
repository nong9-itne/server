package com.example.nong9server.app.member.application

import com.example.nong9server.app.member.consts.MemberRole
import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.GenerateTokenWithLoginRequest
import com.example.nong9server.app.member.dto.GenerateTokenWithRegisterRequest
import com.example.nong9server.app.member.dto.TokenResponse
import com.example.nong9server.app.member.infrastructure.repository.MemberRepository
import com.example.nong9server.common.exception.DuplicateMemberException
import com.example.nong9server.common.exception.MemberNotFoundException
import com.example.nong9server.common.security.JwtTokenProvider
import com.example.nong9server.common.security.sha256Encrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val tokenProvider: JwtTokenProvider,
) {

    fun generateTokenWithLogin(generateTokenWithLoginRequest: GenerateTokenWithLoginRequest): TokenResponse {
        val member = memberRepository.findMemberByAccountId(generateTokenWithLoginRequest.memberId) ?: throw MemberNotFoundException()

        member.authenticate(generateTokenWithLoginRequest.password)

        val token = tokenProvider.createToken(member.accountId)

        return TokenResponse(token)
    }

    fun generateTokenWithRegister(generateTokenWithRegisterRequest: GenerateTokenWithRegisterRequest): TokenResponse {
        val member = memberRepository.findMemberByAccountId(generateTokenWithRegisterRequest.memberId)

        if (member != null) {
            throw DuplicateMemberException()
        }

        val newMember = Member(
            accountId = generateTokenWithRegisterRequest.memberId,
            password = sha256Encrypt(generateTokenWithRegisterRequest.password),
            memberName = generateTokenWithRegisterRequest.memberName,
            role = MemberRole.MEMBER
        )
        memberRepository.registerMember(newMember)

        val token = tokenProvider.createToken(newMember.accountId)

        return TokenResponse(token)
    }

    fun checkMemberExist(id: Long): Boolean {
        memberRepository.findById(id) ?: return false

        return true
    }
}

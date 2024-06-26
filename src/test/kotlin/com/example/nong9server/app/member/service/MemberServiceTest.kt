package com.example.nong9server.app.member.service

import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.app.member.consts.MemberRole
import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.GenerateTokenWithLoginRequest
import com.example.nong9server.app.member.dto.GenerateTokenWithRegisterRequest
import com.example.nong9server.app.member.infrastructure.repository.MemberRepository
import com.example.nong9server.common.exception.DuplicateMemberException
import com.example.nong9server.common.infrastructure.Tx
import com.example.nong9server.common.security.JwtTokenProvider
import com.example.nong9server.common.security.sha256Encrypt
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Tx::class])
@ExtendWith(MockKExtension::class)
class MemberServiceTest {
    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @MockK
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setup() {
        jwtTokenProvider = JwtTokenProvider()
        memberService = MemberService(memberRepository, jwtTokenProvider)
    }

    @Test
    fun `로그인으로 토큰을 생성한다`() {
        // given
        val generateTokenWithLoginRequest = GenerateTokenWithLoginRequest(MEMBER_ID, PASSWORD)
        every { memberRepository.findMemberByAccountId(MEMBER_ID) } returns Member(1L, MEMBER_ID, sha256Encrypt(PASSWORD), "테스터", MemberRole.MEMBER)

        // when
        val tokenResponse = memberService.generateTokenWithLogin(generateTokenWithLoginRequest)

        // then
        val validToken = jwtTokenProvider.isValidToken(tokenResponse.token)
        assertThat(validToken).isTrue()
    }

    @Test
    fun `회원가입으로 토큰을 생성한다`() {
        // given
        val generateTokenWithLoginRequest = GenerateTokenWithRegisterRequest(MEMBER_ID, PASSWORD, "")
        every { memberRepository.findMemberByAccountId(MEMBER_ID) } returns null
        every { memberRepository.registerMember(any()) } just Runs

        // when
        val tokenResponse = memberService.generateTokenWithRegister(generateTokenWithLoginRequest)

        // then
        val validToken = jwtTokenProvider.isValidToken(tokenResponse.token)
        assertThat(validToken).isTrue()
    }

    @Test
    fun `중복된 아이디로 회원 가입하여 실패한다`() {
        // given
        val generateTokenWithLoginRequest = GenerateTokenWithRegisterRequest(MEMBER_ID, PASSWORD, "")
        every { memberRepository.findMemberByAccountId(MEMBER_ID) } returns Member(1L, MEMBER_ID, sha256Encrypt(PASSWORD), "테스터", MemberRole.MEMBER)

        // when
        // then
        assertThrows<DuplicateMemberException> {
            memberService.generateTokenWithRegister(generateTokenWithLoginRequest)
        }
    }


    companion object {
        private const val MEMBER_ID = "testId"
        private const val PASSWORD = "1q2w3e4r"
    }
}

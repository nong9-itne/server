package com.example.nong9server.common

import com.example.nong9server.common.security.JwtTokenProvider
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JwtTokenTest {
    @MockK
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @BeforeEach
    fun setup() {
        jwtTokenProvider = JwtTokenProvider()
    }

    @Test
    fun `유효한 토큰 발금에 성공한다`() {
        // when
        val token = jwtTokenProvider.createToken(MEMBER_ID)

        // then
        val validToken = jwtTokenProvider.isValidToken(token)
        assertThat(validToken).isTrue()

        val subject = jwtTokenProvider.getSubject(token)
        assertThat(subject).isEqualTo(MEMBER_ID)
    }

    @Test
    fun `시간이 지나면 토큰이 유효하지 않는다`() {
        // given
        jwtTokenProvider = JwtTokenProvider(MINUS_FIVE_SECONDS)

        // when
        val token = jwtTokenProvider.createToken(MEMBER_ID)

        //then
        val validToken = jwtTokenProvider.isValidToken(token)
        assertThat(validToken).isFalse()
    }

    companion object {
        private const val MEMBER_ID = "testId"
        private const val MINUS_FIVE_SECONDS = -5L
    }
}

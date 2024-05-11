package com.example.nong9server.common.security

import com.example.nong9server.common.exception.TokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 12

@Component
class JwtTokenProvider(
    private val accessTokenExpirationInMilliseconds: Long = TWELVE_HOURS_IN_MILLISECONDS,
) {
    private val keySpec: SecretKeySpec = SecretKeySpec("일단 만들어보는 농구 어플".toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)

    fun createToken(memberId: String): String {
        val now = Date()
        val expiration = Date(now.time + accessTokenExpirationInMilliseconds)

        return Jwts.builder()
            .expiration(expiration)
            .subject(memberId)
            .signWith(keySpec)
            .compact()
    }

    fun getSubject(token: String): String {
        if (isValidToken(token).not()) {
            throw TokenException()
        }

        return getClaimsJws(token).payload.subject
    }

    fun isValidToken(token: String): Boolean {
        return runCatching {
            getClaimsJws(token)
            true
        }.getOrElse {
            false
        }
    }

    private fun getClaimsJws(token: String): Jws<Claims> = Jwts.parser()
        .verifyWith(keySpec)
        .build()
        .parseSignedClaims(token)
}

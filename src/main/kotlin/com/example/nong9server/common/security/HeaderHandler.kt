package com.example.nong9server.common.security

class HeaderHandler {

    companion object {
        private const val BEARER = "Bearer"

        fun extractBearerToken(authorization: String): String {
            val (tokenType, token) = BearerHeader.splitToTokenFormat(authorization)

            if (tokenType != BEARER) {
                throw IllegalAccessException("Bearer 형식의 토큰이 아닙니다")
            }

            return token
        }
    }
}

class BearerHeader {
    companion object {
        private const val DELIMITER = " "
        private const val BEARER = "Bearer "

        fun splitToTokenFormat(authorization: String): Pair<String, String> {
            return try {
                val tokenFormat = authorization.split(DELIMITER)
                tokenFormat[0] to tokenFormat[1]
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalAccessException()
            }
        }

        fun of(token: String) = BEARER + token
    }
}

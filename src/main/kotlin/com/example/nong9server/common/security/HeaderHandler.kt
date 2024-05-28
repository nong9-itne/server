package com.example.nong9server.common.security

class HeaderHandler {

    companion object {
        private const val BEARER = "Bearer"
        private const val DELIMITER = " "

        fun extractBearerToken(authorization: String): String {
            val (tokenType, token) = splitToTokenFormat(authorization)

            if (tokenType != BEARER) {
                throw IllegalAccessException("Bearer 형식의 토큰이 아닙니다")
            }

            return token
        }

        private fun splitToTokenFormat(authorization: String): Pair<String, String> {
            return try {
                val tokenFormat = authorization.split(DELIMITER)
                tokenFormat[0] to tokenFormat[1]
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalAccessException()
            }
        }
    }
}

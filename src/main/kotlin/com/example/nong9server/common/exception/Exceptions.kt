package com.example.nong9server.common.exception

class TokenException(
    override val message: String = "로그인 정보가 일치하지 않습니다",
) : RuntimeException(message)

class UnidentifiedMemberException(
    override val message: String = "인증되지 않은 사용자입니다",
) : RuntimeException(message)

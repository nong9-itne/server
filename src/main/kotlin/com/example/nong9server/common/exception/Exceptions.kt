package com.example.nong9server.common.exception

class TokenException(
    override val message: String = "토큰 정보가 유효하지 않습니다",
) : RuntimeException(message)

class UnidentifiedMemberException(
    override val message: String = "인증되지 않은 사용자입니다",
) : RuntimeException(message)

class MemberNotFoundException(
    override val message: String = "사용자 정보가 존재하지 않습니다",
) : RuntimeException(message)

class DuplicateMemberException(
    override val message: String = "이미 존재하는 사용자 정보입니다",
) : RuntimeException(message)

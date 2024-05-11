package com.example.nong9server.app.member.dto

data class TokenResponse(
    val token: String
)

data class MemberInfoResponse(
    val memberId: String,
    val memberName: String
)

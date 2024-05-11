package com.example.nong9server.app.member.dto

data class GenerateTokenWithLoginRequest(
    val memberId: String,
    val password: String
)

data class GenerateTokenWithRegisterRequest(
    val memberId: String,
    val password: String,
    val memberName: String
)

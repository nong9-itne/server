package com.example.nong9server.app.member.dto

data class GenerateTokenRequest(
    val memberId: String,
    val password: String
)

package com.example.nong9server.app.member.presentation

import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.app.member.dto.GenerateTokenRequest
import com.example.nong9server.app.member.dto.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/login")
    fun generateTokenWithLogin(@RequestBody generateTokenRequest: GenerateTokenRequest): ResponseEntity<TokenResponse> {
        val tokenResponse = memberService.generateTokenWithLogin(generateTokenRequest)

        return ResponseEntity.ok(tokenResponse)
    }
}

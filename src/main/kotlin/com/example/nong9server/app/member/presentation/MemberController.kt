package com.example.nong9server.app.member.presentation

import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.GenerateTokenWithLoginRequest
import com.example.nong9server.app.member.dto.GenerateTokenWithRegisterRequest
import com.example.nong9server.app.member.dto.MemberInfoResponse
import com.example.nong9server.app.member.dto.TokenResponse
import com.example.nong9server.common.presentation.responseEntity
import com.example.nong9server.common.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/login")
    fun generateTokenWithLogin(@RequestBody generateTokenWithLoginRequest: GenerateTokenWithLoginRequest): ResponseEntity<TokenResponse> {
        val tokenResponse = memberService.generateTokenWithLogin(generateTokenWithLoginRequest)

        return responseEntity {
            body = tokenResponse
        }
    }

    @PostMapping("/register")
    fun generateTokenWithRegister(@RequestBody generateTokenWithRegisterRequest: GenerateTokenWithRegisterRequest): ResponseEntity<TokenResponse> {
        val tokenResponse = memberService.generateTokenWithRegister(generateTokenWithRegisterRequest)

        return responseEntity {
            body = tokenResponse
        }
    }

    @GetMapping("/me")
    fun findMemberInfo(@MemberClaim member: Member): ResponseEntity<MemberInfoResponse> {
        val memberInfoResponse = MemberInfoResponse(
            memberId = member.accountId,
            memberName = member.memberName
        )

        return responseEntity {
            body = memberInfoResponse
        }
    }
}

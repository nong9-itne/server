package com.example.nong9server.common.presentation

import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.common.security.AdminClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController {

    @PostMapping("/validate/authentication")
    fun validateAuthentication(@AdminClaim member: Member): ResponseEntity<Boolean> {
        return responseEntity {
            body = true
        }
    }
}

package com.example.nong9server.app.member.domain

import com.example.nong9server.common.exception.UnidentifiedMemberException
import com.example.nong9server.common.security.sha256Encrypt

class Member(
    val id: Long = 0L,

    val memberId: String,

    var password: String,

    var memberName: String,

    val role: MemberRole
) {

    fun authenticate(verifyPassword: String) = identify(password == sha256Encrypt(verifyPassword)) { "비밀번호가 일치하지 않습니다" }

    private fun identify(value: Boolean, lazyMessage: () -> Any = {}) {
        if (value) {
            return
        }

        throw UnidentifiedMemberException(lazyMessage().toString())
    }

    override fun toString(): String {
        return "Member(id=$id, memberId='$memberId', password='$password', memberName='$memberName', role=$role)"
    }
}

package com.example.nong9server.app.member.infrastructure.entity

import com.example.nong9server.app.member.consts.MemberRole
import com.example.nong9server.common.infrastructure.BaseEntity
import jakarta.persistence.Entity

@Entity
class MemberEntity(
    id: Long = 0L,

    val accountId: String,

    var password: String,

    var memberName: String,

    val role: MemberRole
) : BaseEntity(id) {
}

package com.example.nong9server.app.member.infrastructure.entity

import com.example.nong9server.app.member.domain.MemberRole
import com.example.nong9server.common.infrastructure.BaseEntity
import jakarta.persistence.Entity

@Entity
class MemberEntity(
    id: Long = 0L,

    val memberId: String,

    var password: String,

    var memberName: String,

    val role: MemberRole
) : BaseEntity(id) {
}

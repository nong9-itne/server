package com.example.nong9server.app.member.infrastructure.entity

import com.example.nong9server.common.infrastructure.BaseEntity
import jakarta.persistence.Entity

@Entity
class MemberEntity(
    id: Long = 0L,

    val memberId: String,

    var password: String,

    var username: String,
) : BaseEntity(id) {
}

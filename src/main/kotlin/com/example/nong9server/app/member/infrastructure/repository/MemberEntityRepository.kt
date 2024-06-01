package com.example.nong9server.app.member.infrastructure.repository

import com.example.nong9server.app.member.infrastructure.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberEntityRepository : JpaRepository<MemberEntity, Long> {

    fun findByAccountId(memberId: String): MemberEntity?
}

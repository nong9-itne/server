package com.example.nong9server.app.member.infrastructure.repository

import com.example.nong9server.app.member.domain.Member
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val memberEntityRepository: MemberEntityRepository
) {

    fun findMemberByMemberId(memberId: String): Member {
        val memberEntity = memberEntityRepository.findByMemberId(memberId) ?: throw RuntimeException("Member with id: $memberId not found")

        return Member(
            id = memberEntity.id,
            memberId = memberEntity.memberId,
            password = memberEntity.password,
            username = memberEntity.username,
        )
    }
}

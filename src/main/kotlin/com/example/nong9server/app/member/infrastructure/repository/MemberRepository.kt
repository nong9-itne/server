package com.example.nong9server.app.member.infrastructure.repository

import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.MemberInfoResponse
import com.example.nong9server.app.member.infrastructure.entity.MemberEntity
import com.example.nong9server.app.member.infrastructure.entity.QMemberEntity
import com.example.nong9server.common.exception.MemberNotFoundException
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val memberEntityRepository: MemberEntityRepository,
    private val query: JPAQueryFactory
) {
    fun findById(id: Long): Member? {
        val memberEntity = memberEntityRepository.findByIdOrNull(id) ?: return null

        return Member(
            id = memberEntity.id,
            accountId = memberEntity.accountId,
            password = memberEntity.password,
            memberName = memberEntity.memberName,
            role = memberEntity.role
        )
    }

    fun findMemberByAccountId(memberId: String): Member? {
        val memberEntity = memberEntityRepository.findByAccountId(memberId) ?: return null

        return Member(
            id = memberEntity.id,
            accountId = memberEntity.accountId,
            password = memberEntity.password,
            memberName = memberEntity.memberName,
            role = memberEntity.role
        )
    }

    fun registerMember(member: Member) {
        MemberEntity(
            accountId = member.accountId,
            password = member.password,
            memberName = member.memberName,
            role = member.role
        ).run { memberEntityRepository.save(this) }
    }

    fun findMemberInfo(memberId: String): MemberInfoResponse {
        val qMemberEntity = QMemberEntity.memberEntity

        return query.select(
            Projections.constructor(
                MemberInfoResponse::class.java,
                qMemberEntity.accountId,
                qMemberEntity.memberName
            )
        )
            .from(qMemberEntity)
            .where(qMemberEntity.accountId.eq(memberId))
            .fetchOne() ?: throw MemberNotFoundException()
    }
}

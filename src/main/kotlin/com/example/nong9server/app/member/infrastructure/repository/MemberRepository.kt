package com.example.nong9server.app.member.infrastructure.repository

import com.example.nong9server.app.member.domain.Member
import com.example.nong9server.app.member.dto.MemberInfoResponse
import com.example.nong9server.app.member.infrastructure.entity.MemberEntity
import com.example.nong9server.app.member.infrastructure.entity.QMemberEntity
import com.example.nong9server.common.exception.DuplicateMemberException
import com.example.nong9server.common.exception.MemberNotFoundException
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val memberEntityRepository: MemberEntityRepository,
    private val query: JPAQueryFactory
) {

    fun findMemberByMemberId(memberId: String): Member? {
        val memberEntity = memberEntityRepository.findByMemberId(memberId) ?: return null

        return Member(
            id = memberEntity.id,
            memberId = memberEntity.memberId,
            password = memberEntity.password,
            memberName = memberEntity.memberName,
        )
    }

    fun registerMember(member: Member) {
        MemberEntity(
            memberId = member.memberId,
            password = member.password,
            memberName = member.memberName,
        ).run { memberEntityRepository.save(this) }
    }

    fun findMemberInfo(memberId: String): MemberInfoResponse {
        val qMemberEntity = QMemberEntity.memberEntity

        return query.select(
            Projections.constructor(
                MemberInfoResponse::class.java,
                qMemberEntity.memberId,
                qMemberEntity.memberName
            )
        )
            .from(qMemberEntity)
            .where(qMemberEntity.memberId.eq(memberId))
            .fetchOne() ?: throw MemberNotFoundException()
    }
}

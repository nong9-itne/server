package com.example.nong9server

import com.example.nong9server.app.member.infrastructure.entity.MemberEntity
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Nong9ServerApplication

fun main(args: Array<String>) {
    runApplication<Nong9ServerApplication>(*args)
}

@RestController
class HeartBeatController {
    @GetMapping("/heartbeat")
    fun heartbeat() = "OK"
}

@Component
class InitService(
    private val init: Init
) {

    @PostConstruct
    fun init() {
        init.init()
    }

    companion object {
        @Component
        @Transactional
        class Init(
            private val em: EntityManager
        ) {

            fun init() {
                val memberEntity = MemberEntity(
                    memberId = "testId",
                    password = "1q2w3e4r",
                    username = "테스터"
                )
                em.persist(memberEntity)
            }
        }
    }
}

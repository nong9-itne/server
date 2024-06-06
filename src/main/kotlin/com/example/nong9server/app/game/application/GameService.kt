package com.example.nong9server.app.game.application

import com.example.nong9server.app.game.domain.*
import com.example.nong9server.app.game.dto.RegisterGameEventRequest
import com.example.nong9server.app.game.dto.StartGameRequest
import com.example.nong9server.app.game.infrastructure.repository.GameRepository
import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.common.exception.GameNotFoundException
import com.example.nong9server.common.exception.MemberNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GameService(
    private val gameRepository: GameRepository,
    private val memberService: MemberService
) {

    fun startGame(startGameRequest: StartGameRequest) {
        val game = toDomain(startGameRequest)

        game.judges.forEach { checkMemberExists(it.memberId) }
        (game.team1.players + game.team2.players).forEach { checkMemberExists(it.memberId) }

        gameRepository.registerGame(game)
    }

    fun recordGameEvent(registerGameEventRequest: RegisterGameEventRequest) {
        val game = gameRepository.loadGame(registerGameEventRequest.gameId) ?: throw GameNotFoundException()
        registerGameEventRequest.gameEvents
            .map {
                GameEvent(
                    id = 0L,
                    quarter = it.quarter,
                    teamId = it.teamId,
                    playerId = it.playerId,
                    gameEventType = it.gameEventType,
                    point = it.point,
                    eventDateTime = it.eventDateTime
                )
            }
            .forEach {
                checkMemberExists(it.playerId)
                game.acceptEvent(it)
            }

        gameRepository.updateGame(game)
    }

    fun finishGame() {

    }

    private fun checkMemberExists(memberId: Long) {
        if (memberId == 0L) {
            return
        }

        val checkMemberExist = memberService.checkMemberExist(memberId)
        check(checkMemberExist) { throw MemberNotFoundException() }
    }

    private fun toDomain(startGameRequest: StartGameRequest) = Game(
        id = 0L,
        competition = startGameRequest.competition,
        gameNumber = startGameRequest.gameNumber,
        gameDateTime = startGameRequest.gameDateTime,
        judges = startGameRequest.judges.map { Judge(it.memberId, it.name, it.role) }.toMutableList(),
        team1 = startGameRequest.team1.run {
            Team(
                teamId = teamId,
                name = name,
                players = players.map { Player(it.memberId, it.name, it.number, it.status) }.toMutableList()
            )
        },
        team2 = startGameRequest.team2.run {
            Team(
                teamId = teamId,
                name = name,
                players = players.map { Player(it.memberId, it.name, it.number, it.status) }.toMutableList()
            )
        },
        gameEvents = mutableListOf()
    )
}

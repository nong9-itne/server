package com.example.nong9server.app.game.dto

import com.example.nong9server.app.game.consts.GameEventType
import com.example.nong9server.app.game.consts.JudgeRole
import com.example.nong9server.app.game.consts.PlayerStatus
import java.time.LocalDateTime

data class StartGameRequest(
    val competition: String,
    val gameNumber: Int,
    val gameDateTime: LocalDateTime,
    val judges: List<JudgeRequest>,
    val team1: TeamRequest,
    val team2: TeamRequest,
)

data class JudgeRequest(
    val memberId: Long,
    val name: String,
    val role: JudgeRole
)

data class TeamRequest(
    val teamId: Long,
    val name: String,
    val players: List<PlayerRequest>,
)

data class PlayerRequest(
    val memberId: Long,
    val name: String,
    val number: Int,
    val status: PlayerStatus,
)

data class RegisterGameEventRequest(
    val gameId: Long,
    val gameEvents: List<GameEventRequest>
)

data class GameEventRequest(
    val quarter: Int,
    val teamId: Long,
    val playerId: Long,
    val gameEventType: GameEventType,
    val point: Int,
    val eventDateTime: LocalDateTime
)

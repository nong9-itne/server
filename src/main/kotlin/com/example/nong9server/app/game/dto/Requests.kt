package com.example.nong9server.app.game.dto

import com.example.nong9server.app.game.consts.PlayerStatus
import java.time.LocalDateTime

data class StartGameRequest(
    val competition: String,
    val gameNumber: Int,
    val gameDateTime: LocalDateTime,
    val umpire1: UmpireRequest,
    val umpire2: UmpireRequest,
    val team1: TeamRequest,
    val team2: TeamRequest,
)

data class UmpireRequest(
    val memberId: Long,
    val name: String
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

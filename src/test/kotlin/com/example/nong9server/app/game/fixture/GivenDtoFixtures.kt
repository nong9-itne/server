package com.example.nong9server.app.game.fixture

import com.example.nong9server.app.game.consts.GameEventType
import com.example.nong9server.app.game.consts.JudgeRole
import com.example.nong9server.app.game.consts.PlayerStatus
import com.example.nong9server.app.game.dto.*
import java.time.LocalDateTime

fun aStartGameRequest(
    competition: String = "competition",
    gameNumber: Int = 1,
    gameDateTime: LocalDateTime = LocalDateTime.now(),
    judges: List<JudgeRequest> = listOf(aJudgeRequest()),
    team1: TeamRequest = aTeamRequest(
        teamId = teamId1,
        players = listOf(
            aPlayerRequest(
                memberId = playerId1
            )
        )
    ),
    team2: TeamRequest = aTeamRequest(
        teamId = teamId2,
        players = listOf(
            aPlayerRequest(
                memberId = playerId2
            )
        )
    )
) = StartGameRequest(
    competition = competition,
    gameNumber = gameNumber,
    gameDateTime = gameDateTime,
    judges = judges,
    team1 = team1,
    team2 = team2
)

fun aTeamRequest(
    teamId: Long = teamId1,
    name: String = "team 1",
    players: List<PlayerRequest> = listOf(aPlayerRequest())
) = TeamRequest(
    teamId = teamId,
    name = name,
    players = players
)

fun aPlayerRequest(
    memberId: Long = playerId1,
    name: String = "player 1",
    number: Int = 3,
    status: PlayerStatus = PlayerStatus.STARTING
) = PlayerRequest(
    memberId = memberId,
    name = name,
    number = number,
    status = status
)

fun aJudgeRequest(
    memberId: Long = judgeId1,
    name: String = "judge 1",
    role: JudgeRole = JudgeRole.CHIEF
) = JudgeRequest(
    memberId = memberId,
    name = name,
    role = role
)

fun aRegisterGameEventRequest(
    gameId: Long = 1L,
    gameEvents: List<GameEventRequest> = listOf(aGameEventRequest())
) = RegisterGameEventRequest(
    gameId = gameId,
    gameEvents = gameEvents
)

fun aGameEventRequest(
    quarter: Int = 1,
    teamId: Long = teamId1,
    playerId: Long = playerId1,
    gameEventType: GameEventType = GameEventType.SCORE,
    point: Int = point1,
    eventDateTime: LocalDateTime = LocalDateTime.now()
) = GameEventRequest(
    quarter = quarter,
    teamId = teamId,
    playerId = playerId,
    gameEventType = gameEventType,
    point = point,
    eventDateTime = eventDateTime
)


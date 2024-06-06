package com.example.nong9server.app.game.fixture

import com.example.nong9server.app.game.consts.JudgeRole
import com.example.nong9server.app.game.consts.PlayerStatus
import com.example.nong9server.app.game.domain.*
import java.time.LocalDateTime

fun aGame(
    id: Long = 1L,
    competition: String = "competition",
    gameNumber: Int = 1,
    gameDateTime: LocalDateTime = LocalDateTime.now(),
    judges: MutableList<Judge> = mutableListOf(aJudge()),
    team1: Team = aTeam(
        teamId = teamId1,
        players = mutableListOf(
            aPlayer(
                memberId = playerId1
            )
        ),
    ),
    team2: Team = aTeam(
        teamId = teamId2,
        players = mutableListOf(
            aPlayer(
                memberId = playerId2
            )
        ),
    ),
    gameEvents: MutableList<GameEvent> = mutableListOf()
) = Game(
    id = id,
    competition = competition,
    gameNumber = gameNumber,
    gameDateTime = gameDateTime,
    judges = judges,
    team1 = team1,
    team2 = team2,
    gameEvents = gameEvents
)

fun aJudge(
    memberId: Long = judgeId1,
    name: String = "judge 1",
    role: JudgeRole = JudgeRole.CHIEF
) = Judge(
    memberId = memberId,
    name = name,
    role = role
)

fun aTeam(
    teamId: Long = teamId1,
    name: String = "team 1",
    players: MutableList<Player> = mutableListOf(aPlayer())
) = Team(
    teamId = teamId,
    name = name,
    players = players
)

fun aPlayer(
    memberId: Long = playerId1,
    name: String = "player 1",
    number: Int = 1,
    status: PlayerStatus = PlayerStatus.STARTING
) = Player(
    memberId = memberId,
    name = name,
    number = number,
    status = status
)

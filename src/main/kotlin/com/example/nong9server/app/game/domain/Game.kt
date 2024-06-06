package com.example.nong9server.app.game.domain

import com.example.nong9server.common.exception.GameConsistencyException
import java.time.LocalDateTime

class Game(
    val id: Long,
    val competition: String,
    val gameNumber: Int,
    val gameDateTime: LocalDateTime,
    val judges: MutableList<Judge>,
    val team1: Team,
    val team2: Team,
    val gameEvents: MutableList<GameEvent>
) {

    fun acceptEvent(newGameEvent: GameEvent) {
        if (newGameEvent.teamId == team1.teamId) {
            check(team1.checkPlayerExist(newGameEvent.playerId)) { throw GameConsistencyException() }
            team1.acceptEvent(newGameEvent)
        } else if (newGameEvent.teamId == team2.teamId) {
            check(team2.checkPlayerExist(newGameEvent.playerId)) { throw GameConsistencyException() }
            team2.acceptEvent(newGameEvent)
        }

        gameEvents.add(newGameEvent)
    }
}

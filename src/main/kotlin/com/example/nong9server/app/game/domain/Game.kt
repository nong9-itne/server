package com.example.nong9server.app.game.domain

import java.time.LocalDateTime

class Game(
    val id: Long,
    val competition: String,
    val gameNumber: Int,
    val gameDateTime: LocalDateTime,
    val umpire1: Umpire,
    val umpire2: Umpire,
    val team1: Team,
    val team2: Team,
    val gameEvents: MutableList<GameEvent>
) {

    fun acceptEvent(gameEvent: GameEvent) {
        if (gameEvent.teamId == team1.teamId) {
            team1.acceptEvent(gameEvent)
        } else if (gameEvent.teamId == team2.teamId) {
            team2.acceptEvent(gameEvent)
        }

        gameEvents.add(gameEvent)
    }
}

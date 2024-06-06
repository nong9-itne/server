package com.example.nong9server.app.game.domain

import com.example.nong9server.app.game.consts.GameEventType

class Team(
    val teamId: Long,
    val name: String,
    val players: MutableList<Player>,
    var score: Int = 0,
    var foulCount: Int = 0
) {

    fun acceptEvent(gameEvent: GameEvent) {
        when (gameEvent.gameEventType) {
            GameEventType.SCORE -> score += gameEvent.point
            GameEventType.FOUL -> foulCount += gameEvent.point
        }
    }

    fun checkPlayerExist(playerId: Long): Boolean = players.map { it.memberId }.contains(playerId)

}

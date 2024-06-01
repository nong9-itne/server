package com.example.nong9server.app.game.domain

import com.example.nong9server.app.game.consts.GameEventType
import java.time.LocalDateTime

class GameEvent(
    val id: Long,
    val quarter: Int,
    val teamId: Long,
    val playerId: Long,
    val gameEventType: GameEventType,
    val point: Int,
    val eventDateTime: LocalDateTime
) {
    init {
        when (gameEventType) {
            GameEventType.SCORE -> check(point in 1..3) { "점수는 1 ~ 3점만 입력 가능합니다" }
            GameEventType.FOUL -> check(point == 1) { "파울은 1점만 입력 가능합니다" }
        }

        check(quarter in 1..4) { "게임 쿼터는 1 ~ 4쿼터만 존재합니다" }
    }
}


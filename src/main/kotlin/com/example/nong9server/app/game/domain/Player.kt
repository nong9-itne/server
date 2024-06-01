package com.example.nong9server.app.game.domain

import com.example.nong9server.app.game.consts.PlayerStatus

class Player(
    val memberId: Long,
    val name: String,
    val number: Int,
    val status: PlayerStatus,
) {
}



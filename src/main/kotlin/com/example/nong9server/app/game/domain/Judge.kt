package com.example.nong9server.app.game.domain

import com.example.nong9server.app.game.consts.JudgeRole

class Judge(
    val memberId: Long,
    val name: String,
    val role: JudgeRole
) {
}

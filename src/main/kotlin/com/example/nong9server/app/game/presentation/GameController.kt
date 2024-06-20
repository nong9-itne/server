package com.example.nong9server.app.game.presentation

import com.example.nong9server.app.game.application.GameService
import com.example.nong9server.app.game.dto.RecordGameEventRequest
import com.example.nong9server.app.game.dto.StartGameRequest
import com.example.nong9server.common.presentation.responseEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/game")
class GameController(
    private val gameService: GameService
) {

    @PostMapping("/start")
    fun startGame(@RequestBody startGameRequest: StartGameRequest): ResponseEntity<Long> {
        val gameId = gameService.startGame(startGameRequest)

        return responseEntity {
            body = gameId
        }
    }

    @PostMapping("/record/game-event")
    fun recordGameEvent(@RequestBody recordGameEventRequest: RecordGameEventRequest): ResponseEntity<Long> {
        val gameId = gameService.recordGameEvent(recordGameEventRequest)

        return responseEntity {
            body = gameId
        }
    }
}

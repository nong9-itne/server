package com.example.nong9server.app.game.service

import com.example.nong9server.app.game.application.GameService
import com.example.nong9server.app.game.consts.PlayerStatus
import com.example.nong9server.app.game.dto.PlayerRequest
import com.example.nong9server.app.game.dto.StartGameRequest
import com.example.nong9server.app.game.dto.TeamRequest
import com.example.nong9server.app.game.dto.UmpireRequest
import com.example.nong9server.app.game.infrastructure.repository.GameRepository
import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.common.exception.MemberNotFoundException
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class GameServiceTest {
    @MockK
    private lateinit var gameService: GameService

    @MockK
    private lateinit var memberService: MemberService

    @MockK
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setup() {
        gameService = GameService(gameRepository, memberService)
    }

    @Test
    fun `게임을 시작한다`() {
        // given
        every { gameRepository.registerGame(any()) } just Runs
        every { memberService.checkMemberExist(any()) } returns true
        val startGameRequest = VALID_START_GAME_REQUEST

        // when
        // then
        gameService.startGame(startGameRequest)
    }

    @Test
    fun `존재하지 않은 회원을 팀원으로 지정하면 게임 시작에 실패한다`() {
        // given
        every { gameRepository.registerGame(any()) } just Runs
        every { memberService.checkMemberExist(any()) } returns true
        every { memberService.checkMemberExist(4L) } returns false
        val startGameRequest = VALID_START_GAME_REQUEST

        // when
        // then
        assertThrows<MemberNotFoundException> {
            gameService.startGame(startGameRequest)
        }
    }

    @Test
    fun `존재하지 않은 회원을 심판으로 지정하면 게임 시작에 실패한다`() {
        // given
        every { gameRepository.registerGame(any()) } just Runs
        every { memberService.checkMemberExist(any()) } returns true
        every { memberService.checkMemberExist(1L) } returns false
        val startGameRequest = VALID_START_GAME_REQUEST

        // when
        // then
        assertThrows<MemberNotFoundException> {
            gameService.startGame(startGameRequest)
        }
    }

    companion object {
        private val VALID_START_GAME_REQUEST = StartGameRequest(
            competition = "competition",
            gameNumber = 1,
            gameDateTime = LocalDateTime.now(),
            umpire1 = UmpireRequest(
                memberId = 1L,
                name = "심판1"
            ),
            umpire2 = UmpireRequest(
                memberId = 0L,
                name = "심판2"
            ),
            team1 = TeamRequest(
                teamId = 1L,
                name = "team 1",
                players = listOf(
                    PlayerRequest(
                        memberId = 3L,
                        name = "player 1",
                        number = 3,
                        status = PlayerStatus.STARTING
                    ),
                    PlayerRequest(
                        memberId = 4L,
                        name = "player 2",
                        number = 4,
                        status = PlayerStatus.STARTING
                    )
                    ,
                    PlayerRequest(
                        memberId = 0L,
                        name = "player 3",
                        number = 14,
                        status = PlayerStatus.STARTING
                    )
                )
            ),
            team2 = TeamRequest(
                teamId = 0L,
                name = "team 2",
                players = listOf(
                    PlayerRequest(
                        memberId = 0L,
                        name = "player 4",
                        number = 5,
                        status = PlayerStatus.STARTING
                    ),
                    PlayerRequest(
                        memberId = 0L,
                        name = "player 5",
                        number = 4,
                        status = PlayerStatus.STARTING
                    ),
                    PlayerRequest(
                        memberId = 0L,
                        name = "player 6",
                        number = 11,
                        status = PlayerStatus.STARTING
                    )
                )
            )
        )
    }
}

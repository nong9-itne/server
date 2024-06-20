package com.example.nong9server.app.game.service

import com.example.nong9server.app.game.application.GameService
import com.example.nong9server.app.game.fixture.*
import com.example.nong9server.app.game.infrastructure.repository.GameRepository
import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.common.exception.GameConsistencyException
import com.example.nong9server.common.exception.MemberNotFoundException
import com.example.nong9server.common.infrastructure.Tx
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Tx::class])
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

        val startGameRequest = aStartGameRequest()

        // when
        // then
        gameService.startGame(startGameRequest)
    }

    @Test
    fun `존재하지 않은 회원을 팀원으로 지정하면 게임 시작에 실패한다`() {
        // given
        every { gameRepository.registerGame(any()) } just Runs
        every { memberService.checkMemberExist(any()) } returns true
        every { memberService.checkMemberExist(playerId1) } returns false

        val startGameRequest = aStartGameRequest(
            team1 = aTeamRequest(
                players = listOf(
                    aPlayerRequest(
                        memberId = playerId1
                    )
                )
            )
        )

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
        every { memberService.checkMemberExist(judgeId1) } returns false

        val startGameRequest = aStartGameRequest(
            judges = mutableListOf(
                aJudgeRequest(
                    memberId = judgeId1
                )
            )
        )

        // when
        // then
        assertThrows<MemberNotFoundException> {
            gameService.startGame(startGameRequest)
        }
    }

    @Test
    fun `시작한 게임에 기록을 추가한다`() {
        // given
        every { gameRepository.loadGame(1L) } returns aGame()
        every { memberService.checkMemberExist(any()) } returns true
        every { gameRepository.updateGame(any()) } just Runs

        // when
        // then
        gameService.recordGameEvent(aRegisterGameEventRequest())
    }

    @Test
    fun `유효한 쿼터를 지정하지 않으면 기록 추가에 실패한다`() {
        // given
        every { gameRepository.loadGame(1L) } returns aGame(currentQuarter = quarter1)
        every { memberService.checkMemberExist(any()) } returns true
        every { gameRepository.updateGame(any()) } just Runs
        val recordGameEventRequest = aRegisterGameEventRequest(
            gameEvents = listOf(
                aGameEventRequest(
                    quarter = quarter2
                )
            )
        )

        // when
        // then
        assertThrows<GameConsistencyException> {
            gameService.recordGameEvent(recordGameEventRequest)
        }
    }
}

package com.example.nong9server.app.game.application

import com.example.nong9server.app.game.domain.Game
import com.example.nong9server.app.game.domain.Player
import com.example.nong9server.app.game.domain.Team
import com.example.nong9server.app.game.domain.Umpire
import com.example.nong9server.app.game.dto.StartGameRequest
import com.example.nong9server.app.game.infrastructure.repository.GameRepository
import com.example.nong9server.app.member.application.MemberService
import com.example.nong9server.common.exception.MemberNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GameService(
    private val gameRepository: GameRepository,
    private val memberService: MemberService
) {

    fun startGame(startGameRequest: StartGameRequest) {
        val game = toDomain(startGameRequest)

        validate(game)

        gameRepository.registerGame(game)
    }

    private fun validate(game: Game) {
        validateUmpire(game.umpire1)
        validateUmpire(game.umpire2)
        validateTeam(game.team1)
        validateTeam(game.team2)
    }

    private fun validateUmpire(umpire: Umpire) {
        if (umpire.memberId == 0L) {
            return
        }

        val checkMemberExist = memberService.checkMemberExist(umpire.memberId)
        check(checkMemberExist) { throw MemberNotFoundException("존재하지 않는 회원을 심판으로 지정하였습니다.") }
    }

    private fun validateTeam(team: Team) {
        if (team.teamId == 0L) {
            return
        }

        for (player in team.players) {
            if (player.memberId == 0L) {
                continue
            }

            val checkMemberExist = memberService.checkMemberExist(player.memberId)
            check(checkMemberExist) { throw MemberNotFoundException("존재하지 않는 회원을 플레이어로 지정하였습니다.") }
        }
    }

    fun recordGameEvent() {

    }

    fun finishGame() {

    }

    private fun toDomain(startGameRequest: StartGameRequest) = Game(
        id = 0L,
        competition = startGameRequest.competition,
        gameNumber = startGameRequest.gameNumber,
        gameDateTime = startGameRequest.gameDateTime,
        umpire1 = startGameRequest.umpire1.run {
            Umpire(
                memberId = memberId,
                name = name
            )
        },
        umpire2 = startGameRequest.umpire2.run {
            Umpire(
                memberId = memberId,
                name = name
            )
        },
        team1 = startGameRequest.team1.run {
            Team(
                teamId = teamId,
                name = name,
                players = players.map { Player(it.memberId, it.name, it.number, it.status) }
            )
        },
        team2 = startGameRequest.team2.run {
            Team(
                teamId = teamId,
                name = name,
                players = players.map { Player(it.memberId, it.name, it.number, it.status) }
            )
        },
        gameEvents = mutableListOf()
    )
}

package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.model.*
import com.zenika.nckotlinserver.repository.InternalStateRepository
import com.zenika.nckotlinserver.repository.PlayerRepository
import com.zenika.nckotlinserver.repository.PlayerResultRepository
import com.zenika.nckotlinserver.repository.ScenarioRepository
import com.zenika.nckotlinserver.service.executor.Executor
import com.zenika.nckotlinserver.service.executor.In
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime.now
import javax.ws.rs.BadRequestException
import javax.ws.rs.ForbiddenException
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException

@Service
class PlayerService {

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var scenarioRepository: ScenarioRepository

    @Autowired
    lateinit var stateRepository: InternalStateRepository

    @Autowired
    lateinit var resultRepository: PlayerResultRepository

    @Autowired
    lateinit var executor: Executor

    fun createPlayer(playerCreation: PlayerCreation): Player {
        if (playerCreation.name.isBlank()) throw BadRequestException("Empty player name")
        if (playerCreation.mail.isBlank()) throw BadRequestException("Empty player mail")
        if (playerCreation.language.isBlank()) throw BadRequestException("Empty player language")

        // TODO put back mail already played verification ?

        // TODO add language exists verification ?

        val player = playerRepository.save(Player(playerCreation.name, playerCreation.mail, playerCreation.language))

        stateRepository.save(InternalState(playerId = player.playerId))

        return player
    }

    fun getPlayer(playerId: String) = playerRepository.findById(playerId)
            .orElseThrow { NotFoundException("Unknown player $playerId") }

    fun getState(playerId: String) = withPlayerScenarioAndState(playerId, { _, scenario, state ->
        val step = scenario.steps[state.step]
        State(
                scenario.mapImg,
                scenario.avatarImg,
                state.finished,
                step.title,
                step.text,
                step.mapPosition,
                step.template,
                step.tests,
                state.score
        )
    })

    fun test(playerId: String, code: Code) = withPlayerScenarioAndState(playerId, { player, scenario, state ->
        if (state.finished) throw ForbiddenException("Player $playerId has already finished")

        scenario.steps[state.step].tests
                .map { test ->
                    val out = executor.execute(player.language, In(code.code, test.input))
                    TestResult(
                            out.exitCode == 0 && out.out.trimEnd('\n') == test.output,
                            out.out,
                            out.err
                    )
                }
    })

    fun validate(playerId: String, code: Code) = withPlayerScenarioAndState(playerId, { player, scenario, state ->
        if (state.finished) throw ForbiddenException("Player $playerId has already finished")

        val step = scenario.steps[state.step]

        var success = true
        var successCount = 0

        step.validations.forEach {
            val out = executor.execute(player.language, In(code.code, it.input))

            if (out.exitCode == 0 && out.out.trimEnd('\n') == it.output) successCount++
            else success = false
        }

        val result = when {
            success -> step.results.success
            step.results.partialSuccess?.threshold!!.let { successCount >= it } ?: false -> step.results.partialSuccess
            else -> step.results.failure
        }

        val newState = stateRepository.save(InternalState(
                playerId,
                result.finish,
                if (result.finish) state.step else result.step!!,
                state.score + result.score
        ))

        if (newState.finished) {
            resultRepository.add(PlayerResult(
                    player,
                    newState.score,
                    Duration.between(player.startTime, now())
            ))
        }

        ValidateResult(
                successCount.toDouble() / step.validations.size,
                result.score,
                result.text
        )
    })

    fun <R> withPlayerScenarioAndState(playerId: String, callback: (Player, Scenario, InternalState) -> R): R {
        val player = playerRepository.findById(playerId)
                .orElseThrow { NotFoundException("Unknown player $playerId") }
        val scenario = scenarioRepository.findById(player.language)
                .orElseThrow { InternalServerErrorException("Player $playerId has language ${player.language} with no scenario") }
        val state = stateRepository.findById(playerId)
                .orElseThrow { InternalServerErrorException("No state for player $playerId") }

        return callback(player, scenario, state)
    }
}
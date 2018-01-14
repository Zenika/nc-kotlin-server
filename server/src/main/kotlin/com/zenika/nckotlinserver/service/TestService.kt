package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.model.Code
import com.zenika.nckotlinserver.model.TestResult
import com.zenika.nckotlinserver.repository.InternalStateRepository
import com.zenika.nckotlinserver.repository.PlayerRepository
import com.zenika.nckotlinserver.repository.ScenarioRepository
import com.zenika.nckotlinserver.service.executor.Executor
import com.zenika.nckotlinserver.service.executor.In
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.ws.rs.ForbiddenException
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException

@Service
class TestService {
    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var scenarioRepository: ScenarioRepository

    @Autowired
    lateinit var internalStateRepository: InternalStateRepository

    @Autowired
    lateinit var executor: Executor

    fun executeTest(playerId: String, code: Code): List<TestResult> {
        val player = playerRepository.findById(playerId)
                .orElseThrow { NotFoundException("Unknown player $playerId") }
        val scenario = scenarioRepository.findById(player.language)
                .orElseThrow { InternalServerErrorException("Player $playerId has language ${player.language} with no scenario") }
        val internalState = internalStateRepository.findById(playerId)
                .orElseThrow { InternalServerErrorException("No state for player $playerId") }

        if (internalState.finished) {
            throw ForbiddenException("Player $playerId has already finished")
        }

        val step = scenario.steps[internalState.step]

        return step.tests
                .map { test ->
                    val out = executor.execute(player.language, In(code.code, test.input))
                    TestResult(
                            out.exitCode == 0 && out.out.trimEnd('\n') == test.output,
                            out.out,
                            out.err
                    )
                }
    }
}
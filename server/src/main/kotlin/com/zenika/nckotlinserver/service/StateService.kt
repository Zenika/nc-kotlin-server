package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.model.State
import com.zenika.nckotlinserver.repository.InternalStateRepository
import com.zenika.nckotlinserver.repository.PlayerRepository
import com.zenika.nckotlinserver.repository.ScenarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.NotFoundException

@Service
class StateService {
    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var scenarioRepository: ScenarioRepository

    @Autowired
    lateinit var internalStateRepository: InternalStateRepository

    fun getState(playerId: String): State {
        val player = playerRepository.findById(playerId)
                .orElseThrow { NotFoundException("Unknown player $playerId") }
        val scenario = scenarioRepository.findById(player.language)
                .orElseThrow { InternalServerErrorException("Player $playerId has language ${player.language} with no scenario") }
        val internalState = internalStateRepository.findById(playerId)
                .orElseThrow { InternalServerErrorException("No state for player $playerId") }

        val step = scenario.steps[internalState.step]

        return State(
                scenario.mapImg,
                scenario.avatarImg,
                internalState.finished,
                step.title,
                step.text,
                step.mapPosition,
                step.template,
                step.tests,
                internalState.score
        )
    }
}
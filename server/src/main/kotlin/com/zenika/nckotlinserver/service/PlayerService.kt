package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.model.InternalState
import com.zenika.nckotlinserver.model.Player
import com.zenika.nckotlinserver.model.PlayerCreation
import com.zenika.nckotlinserver.repository.InternalStateRepository
import com.zenika.nckotlinserver.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

@Service
class PlayerService {

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var internalStateRepository: InternalStateRepository

    fun createPlayer(playerCreation: PlayerCreation): Player {
        if (playerCreation.name.isBlank()) throw BadRequestException("Empty player name")
        if (playerCreation.mail.isBlank()) throw BadRequestException("Empty player mail")
        if (playerCreation.language.isBlank()) throw BadRequestException("Empty player language")

        // TODO put back mail already played verification ?

        // TODO add language exists verification ?

        val player = playerRepository.save(Player(playerCreation.name, playerCreation.mail, playerCreation.language))

        internalStateRepository.save(InternalState(playerId = player.playerId))

        return player
    }

    fun getPlayer(playerId: String) = playerRepository.findById(playerId)
            .orElseThrow { NotFoundException("Unknown player $playerId") }
}
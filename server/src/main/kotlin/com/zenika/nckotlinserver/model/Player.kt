package com.zenika.nckotlinserver.model

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID.randomUUID

/**
 * Holds information about a player session
 */
data class Player(
        val playerId: String,
        val name: String,
        val mail: String,
        val language: String,
        val startTime: LocalDateTime
) : Entity {
    constructor(name: String, mail: String, language: String) : this(randomUUID().toString(), name, mail, language, now())

    override fun id() = playerId
}

data class PlayerCreation(
        val name: String,
        val mail: String,
        val language: String
)

/**
 * Represents the result of a player
 */
data class PlayerResult(
        val player: Player,
        val score: Int,
        val time: String
)
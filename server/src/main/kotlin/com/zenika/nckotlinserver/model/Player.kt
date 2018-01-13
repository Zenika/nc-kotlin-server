package com.zenika.nckotlinserver.model

import java.time.LocalTime

/**
 * Holds information about a player session
 */
data class Player(
        override val id: String,
        val name: String,
        val mail: String,
        val language: String,
        val startTime: LocalTime
): Entity

/**
 * Represents the result of a player
 */
data class PlayerResult(
        val player: Player,
        val score: Int,
        val time: String
)
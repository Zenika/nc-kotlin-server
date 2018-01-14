package com.zenika.nckotlinserver.model

/**
 * Holds public information about the state of a player
 */
data class State(
        val mapImg: String,
        val avatarImg: String,
        val finished: Boolean,
        val title: String,
        val text: String,
        val mapPosition: Coord,
        val template: String,
        val tests: Array<Test>,
        val score: Int
)

/**
 * Holds private information about the state of a player
 */
data class InternalState(
        val playerId: String,
        val finished: Boolean,
        val step: Int,
        val score: Int
) : Entity {
    override fun id(): String = playerId
}

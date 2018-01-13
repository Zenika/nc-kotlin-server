package com.zenika.nckotlinserver.model

/**
 * Holds all the information of a scenario
 */
data class Scenario(
        override val id: String,
        val language: String,
        val avatarImg: String?
): Entity

/**
 * Holds the information of one step of a scenario
 */
data class Step(
        val title: String,
        val text: String,
        val mapPosition: MapPosition,
        val template: String,
        val tests: Array<Test>,
        val Validations: Array<Test>
)

data class MapPosition(
        val x: Int,
        val y: Int
)

/**
 * Defines an expected result for a step of a scenario
 */
data class Result(
        val finish: Boolean,
        val step: Step,
        val score: Int,
        val threshold: Int,
        val text: String
)
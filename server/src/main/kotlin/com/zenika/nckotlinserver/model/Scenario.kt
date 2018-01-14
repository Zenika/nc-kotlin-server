package com.zenika.nckotlinserver.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

/**
 * Holds all the information of a scenario
 */
@JsonInclude(NON_NULL)
data class Scenario(
        val language: String,
        val mapImg: String,
        val avatarImg: String,
        val steps: Array<Step>
) : Entity {
    override fun id() = language
}

/**
 * Holds the information of one step of a scenario
 */
data class Step(
        val title: String,
        val text: String,
        val mapPosition: Coord,
        val template: String,
        val tests: Array<Test>,
        val validations: Array<Test>,
        val results: Results
)

@JsonInclude(NON_NULL)
data class Results(
        val success: Result,
        val partialSuccess: Result?,
        val failure: Result
)

/**
 * Defines an expected result for a step of a scenario
 */
@JsonInclude(NON_NULL)
data class Result(
        val finish: Boolean,
        val step: Int?,
        val score: Int,
        val threshold: Int?,
        val text: String?
)
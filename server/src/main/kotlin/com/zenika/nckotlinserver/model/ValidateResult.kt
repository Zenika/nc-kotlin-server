package com.zenika.nckotlinserver.model

/**
 * Represents the result of the valiation of a step
 */
data class ValidateResult(
    val rate:  Double,
    val score: Int,
    val text: String
)
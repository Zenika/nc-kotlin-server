package com.zenika.nckotlinserver.model

/**
 * Represents the result of the validation of a step
 */
data class ValidateResult(
    val rate:  Double,
    val score: Int,
    val text: String?
)
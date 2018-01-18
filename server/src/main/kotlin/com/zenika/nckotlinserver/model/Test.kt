package com.zenika.nckotlinserver.model

/**
 * Defines a test/validation for a step of a scenario
 */
data class Test(
    val title: String,
    val input: String,
    val output: String
)

/**
 * Represents the result of a test/validation
 */
data class TestResult(
    val ok: Boolean,
    val out: String,
    val err: String
)
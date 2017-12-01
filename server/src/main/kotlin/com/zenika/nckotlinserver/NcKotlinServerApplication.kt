package com.zenika.nckotlinserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NcKotlinServerApplication

fun main(args: Array<String>) {
    runApplication<NcKotlinServerApplication>(*args)
}

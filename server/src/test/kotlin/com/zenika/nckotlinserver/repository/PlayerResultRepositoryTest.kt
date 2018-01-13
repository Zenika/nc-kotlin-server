package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.Player
import com.zenika.nckotlinserver.model.PlayerResult
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalTime
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class PlayerResultRepositoryTest {

    @Autowired
    lateinit var playerResultRepository: PlayerResultRepository

    @Test
    fun save_player() {
        val id = Random().nextInt(10000).toString()
        val playerResult = PlayerResult(Player(id, "John Doe", "john.doe@zenika.com", "kotlin", LocalTime.now()),
                50, "4m50")
        playerResultRepository.add(playerResult)
        val playerResults = playerResultRepository.getAll()
        assertEquals(playerResult, playerResults?.last())
    }
}
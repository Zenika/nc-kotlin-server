package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.Player
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalTime
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class PlayerRepositoryTest {

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Test
    fun save_player() {
        val id = Random().nextInt(10000).toString()
        val player = Player(id, "John Doe", "john.doe@zenika.com", "kotlin", LocalTime.now())
        playerRepository.save(player)
        assertEquals("John Doe", playerRepository.findById(id).get().name)
    }
}
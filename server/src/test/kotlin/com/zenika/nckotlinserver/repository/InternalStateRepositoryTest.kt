package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.InternalState
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class InternalStateRepositoryTest {

    @Autowired
    lateinit var internalStateRepository: InternalStateRepository

    @Test
    fun should_store_internal_state() {
        val playerId = Random().nextInt(10000).toString()
        val internalState = InternalState(playerId, false, 1, 10)
        internalStateRepository.set(internalState)
        assertEquals(internalState, internalStateRepository.get(playerId))
    }
}
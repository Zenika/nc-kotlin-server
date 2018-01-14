package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.Scenario
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ScenarioRepositoryTest {

    @Autowired
    lateinit var scenarioRepository: ScenarioRepository

    @Test
    fun save_scenario() {
        val id = Random().nextInt(10000).toString()
        val scenario = Scenario(id, "kotlin", "", emptyList())
        scenarioRepository.set(scenario)
        assertEquals(scenario, scenarioRepository.get(id))
    }
}
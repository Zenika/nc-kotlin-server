package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.repository.PlayerResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResultService {
    @Autowired
    lateinit var repository: PlayerResultRepository

    fun list() = repository.getAll()
        ?.sortedBy { it.time }
        ?.sortedByDescending { it.score }
}
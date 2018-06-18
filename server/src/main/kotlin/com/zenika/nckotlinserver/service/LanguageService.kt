package com.zenika.nckotlinserver.service

import com.zenika.nckotlinserver.model.Language
import com.zenika.nckotlinserver.repository.ScenarioRepository
import com.zenika.nckotlinserver.service.executor.Executor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LanguageService {

    @Autowired
    lateinit var executor: Executor

    @Autowired
    lateinit var scenarioRepository: ScenarioRepository

    fun list() = executor.getLanguages()
        .mapNotNull { scenarioRepository.get(it) }
        .map { Language(it.language, it.avatarImg) }

    fun isAvailable(language: String) = executor.getLanguages().contains(language) && scenarioRepository.exists(language)
}
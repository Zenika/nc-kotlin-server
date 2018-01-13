package com.zenika.nckotlinserver.resources

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

@Configuration
class JerseyConfig : ResourceConfig() {
    init {
        packages("com.zenika.nckotlinserver.resources")
    }
}
package de.codecentric.vanilla.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "application")
data class ApplicationProperties(
    val greeting: String,
)

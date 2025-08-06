package de.codecentric.vanilla

import de.codecentric.vanilla.properties.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class VanillaApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<VanillaApplication>(*args)
}

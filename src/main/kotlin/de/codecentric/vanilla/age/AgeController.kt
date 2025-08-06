package de.codecentric.vanilla.age

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class AgeController(
    private val ageCalculator: AgeCalculator,
) {

    @GetMapping("/api/age")
    fun calculateAge(@RequestParam birthdate: LocalDate): ResponseEntity<Map<String, Any>> {
        if (birthdate.isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body(
                mapOf("error" to "Birthdate cannot be in the future"),
            )
        }

        val age = ageCalculator.calculateAge(birthdate)
        return ResponseEntity.ok(mapOf("age" to age))
    }
}

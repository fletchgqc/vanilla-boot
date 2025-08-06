package de.codecentric.vanilla.age

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AgeCalculatorTest {

    private val subject = AgeCalculator()

    @Test
    fun `should calculate age correctly`() {
        val birthdate = LocalDate.now().minusYears(25)
        val age = subject.calculateAge(birthdate)
        age shouldBe 25
    }
}

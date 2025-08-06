package de.codecentric.vanilla.age

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class AgeCalculator {

    fun calculateAge(birthdate: LocalDate): Int {
        return Period.between(birthdate, LocalDate.now()).years
    }
}

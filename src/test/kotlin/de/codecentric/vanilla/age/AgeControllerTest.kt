package de.codecentric.vanilla.age

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@WebMvcTest(AgeController::class)
class AgeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var ageCalculator: AgeCalculator

    @Test
    fun `should return age for valid birthdate`() {
        val birthdate = LocalDate.of(1990, 1, 1)
        every { ageCalculator.calculateAge(birthdate) } returns 34

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/age")
                .param("birthdate", "1990-01-01"),
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(34))
    }

    @Test
    fun `should return 400 for invalid date format`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/age")
                .param("birthdate", "01/01/1990"),
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should return 400 for future birthdate`() {
        val futureBirthdate = LocalDate.now().plusDays(1).toString()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/age")
                .param("birthdate", futureBirthdate),
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.error")
                    .value("Birthdate cannot be in the future"),
            )
    }
}

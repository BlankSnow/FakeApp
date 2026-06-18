package com.blank.fakeapp.data.mapper

import com.blank.fakeapp.data.remote.dto.UserDto
import com.blank.fakeapp.data.remote.dto.UserNameDto
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun `UserDto toDomain maps correctly`() {
        // Given
        val dto = UserDto(
            id = 8,
            email = "test@example.com",
            username = "johndoe",
            name = UserNameDto(firstname = "John", lastname = "Doe"),
            phone = "123456789"
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals(dto.id, domain.id)
        assertEquals(dto.email, domain.email)
        assertEquals(dto.username, domain.username)
        assertEquals(dto.name.firstname, domain.firstName)
        assertEquals(dto.name.lastname, domain.lastName)
        assertEquals(dto.phone, domain.phone)
    }
}

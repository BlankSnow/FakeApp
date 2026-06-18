package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.data.remote.dto.UserDto
import com.blank.fakeapp.data.remote.dto.UserNameDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var api: FakeStoreApi
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        repository = UserRepositoryImpl(api)
    }

    @Test
    fun `getUser returns success when API call is successful`() = runTest {
        // Given
        val userId = 8
        val userDto = UserDto(userId, "test@test.com", "user", UserNameDto("John", "Doe"), "123")
        coEvery { api.getUser(userId) } returns userDto

        // When
        val result = repository.getUser(userId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("John", result.getOrNull()?.firstName)
    }

    @Test
    fun `getUser returns failure when API call fails`() = runTest {
        // Given
        val userId = 8
        val exception = Exception("API error")
        coEvery { api.getUser(userId) } throws exception

        // When
        val result = repository.getUser(userId)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}

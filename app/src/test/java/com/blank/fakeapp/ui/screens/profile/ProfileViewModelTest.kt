package com.blank.fakeapp.ui.screens.profile

import app.cash.turbine.test
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.model.User
import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val getUserUseCase: GetUserUseCase = mockk()
    private val getFavoritesUseCase: GetFavoritesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        // Given
        val favoritesFlow = MutableStateFlow<List<Product>>(emptyList())
        every { getFavoritesUseCase() } returns favoritesFlow
        coEvery { getUserUseCase(any()) } returns Result.success(mockk(relaxed = true))

        // When
        val viewModel = ProfileViewModel(getUserUseCase, getFavoritesUseCase)

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(UserState.Loading, initialState.userState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadUser success updates uiState with user and favorites count`() = runTest {
        // Given
        val user = User(8, "test@test.com", "user", "John", "Doe", "123")
        val favoritesFlow = MutableStateFlow<List<Product>>(emptyList())

        coEvery { getUserUseCase(8) } returns Result.success(user)
        every { getFavoritesUseCase() } returns favoritesFlow

        // When
        val viewModel = ProfileViewModel(getUserUseCase, getFavoritesUseCase)

        // Then
        viewModel.uiState.test {
            // Skip initial loading
            assertTrue(awaitItem().userState is UserState.Loading)
            
            advanceUntilIdle()
            
            val successState = awaitItem()
            assertTrue(successState.userState is UserState.Success)
            assertEquals(user, (successState.userState as UserState.Success).user)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadUser failure updates uiState with error`() = runTest {
        // Given
        val errorMessage = "Network Error"
        val favoritesFlow = MutableStateFlow<List<Product>>(emptyList())

        coEvery { getUserUseCase(8) } returns Result.failure(Exception(errorMessage))
        every { getFavoritesUseCase() } returns favoritesFlow

        // When
        val viewModel = ProfileViewModel(getUserUseCase, getFavoritesUseCase)

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem().userState is UserState.Loading)
            
            advanceUntilIdle()
            
            val errorState = awaitItem()
            assertTrue(errorState.userState is UserState.Error)
            assertEquals(errorMessage, (errorState.userState as UserState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `favorites update dynamically updates favoritesCount`() = runTest {
        // Given
        val user = User(8, "test@test.com", "user", "John", "Doe", "123")
        val favoritesFlow = MutableStateFlow<List<Product>>(emptyList())

        coEvery { getUserUseCase(8) } returns Result.success(user)
        every { getFavoritesUseCase() } returns favoritesFlow

        val viewModel = ProfileViewModel(getUserUseCase, getFavoritesUseCase)

        viewModel.uiState.test {
            // Loading
            awaitItem()
            
            advanceUntilIdle()
            
            // Success with 0 favorites
            assertEquals(0, awaitItem().favoritesCount)

            // Update favorites
            favoritesFlow.value = listOf(mockk(), mockk(), mockk())

            // Should emit new state with count 3
            val updatedState = awaitItem()
            assertEquals(3, updatedState.favoritesCount)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}

package com.blank.fakeapp.ui.screens.favorites

import app.cash.turbine.test
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val getFavoritesUseCase: GetFavoritesUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
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
        every { getFavoritesUseCase() } returns flowOf()

        // When
        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.uiState.test {
            assertEquals(FavoritesUiState.Loading, awaitItem())
        }
    }

    @Test
    fun `load favorites success updates uiState`() = runTest {
        // Given
        val favorites = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Image", isFavorite = true)
        )
        every { getFavoritesUseCase() } returns flowOf(favorites)

        // When
        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem() is FavoritesUiState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is FavoritesUiState.Success)
            assertEquals(favorites, (state as FavoritesUiState.Success).products)
        }
    }

    @Test
    fun `removeFromFavorites calls toggleFavoriteUseCase`() = runTest {
        // Given
        val product = Product(1, "P1", 10.0, "D", "C", "I", isFavorite = true)
        every { getFavoritesUseCase() } returns flowOf(emptyList())
        coEvery { toggleFavoriteUseCase(product) } returns Unit

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        // When
        viewModel.removeFromFavorites(product)
        advanceUntilIdle()

        // Then
        coVerify { toggleFavoriteUseCase(product) }
    }
}

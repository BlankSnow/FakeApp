package com.blank.fakeapp.ui.screens.products

import app.cash.turbine.test
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
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
class ProductListViewModelTest {

    private val getProductsUseCase: GetProductsUseCase = mockk()
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
        every { getProductsUseCase() } returns flowOf()

        // When
        val viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.uiState.test {
            assertEquals(ProductUiState.Loading, awaitItem())
        }
    }

    @Test
    fun `load products success updates uiState`() = runTest {
        // Given
        val products = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Image", isFavorite = false)
        )
        every { getProductsUseCase() } returns flowOf(Result.success(products))

        // When
        val viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem() is ProductUiState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is ProductUiState.Success)
            assertEquals(products, (state as ProductUiState.Success).products)
        }
    }

    @Test
    fun `load products failure updates uiState with error`() = runTest {
        // Given
        val errorMessage = "API Error"
        every { getProductsUseCase() } returns flowOf(Result.failure(Exception(errorMessage)))

        // When
        val viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        // Then
        viewModel.uiState.test {
            assertTrue(awaitItem() is ProductUiState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is ProductUiState.Error)
            assertEquals(errorMessage, (state as ProductUiState.Error).message)
        }
    }

    @Test
    fun `toggleFavorite calls toggleFavoriteUseCase`() = runTest {
        // Given
        val product = Product(1, "P1", 10.0, "D", "C", "I")
        every { getProductsUseCase() } returns flowOf(Result.success(emptyList()))
        coEvery { toggleFavoriteUseCase(product) } returns Unit

        val viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        // When
        viewModel.toggleFavorite(product)
        advanceUntilIdle()

        // Then
        coVerify { toggleFavoriteUseCase(product) }
    }
}

package com.blank.fakeapp.domain.usecase

import app.cash.turbine.test
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var useCase: GetProductsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetProductsUseCase(repository)
    }

    @Test
    fun `invoke should emit success with mapped favorites when repository returns data`() = runTest {
        // Given
        val remoteProducts = listOf(
            Product(1, "P1", 10.0, "D1", "C1", "I1", isFavorite = false),
            Product(2, "P2", 20.0, "D2", "C2", "I2", isFavorite = false)
        )
        val favoritesFlow = MutableStateFlow(listOf(remoteProducts[0])) // ID 1 is favorite

        coEvery { repository.getProducts() } returns Result.success(remoteProducts)
        every { repository.getFavoriteProducts() } returns favoritesFlow

        // When & Then
        useCase().test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            
            val products = result.getOrNull()!!
            assertEquals(2, products.size)
            assertTrue(products[0].isFavorite) // Should be true because it's in favoritesFlow
            assertTrue(!products[1].isFavorite)
            
            cancelAndIgnoreRemainingEvents()
        }
        
        coVerify(exactly = 1) { repository.getProducts() }
    }

    @Test
    fun `invoke should emit update when favorites change without calling API again`() = runTest {
        // Given
        val remoteProducts = listOf(Product(1, "P1", 10.0, "D1", "C1", "I1", isFavorite = false))
        val favoritesFlow = MutableStateFlow<List<Product>>(emptyList())

        coEvery { repository.getProducts() } returns Result.success(remoteProducts)
        every { repository.getFavoriteProducts() } returns favoritesFlow

        useCase().test {
            // Initial state (no favorites)
            val firstEmission = awaitItem().getOrNull()!!
            assertTrue(!firstEmission[0].isFavorite)

            // Simulate Room update: Add product 1 to favorites
            favoritesFlow.value = listOf(remoteProducts[0])

            // Second emission (should be updated)
            val secondEmission = awaitItem().getOrNull()!!
            assertTrue(secondEmission[0].isFavorite)

            cancelAndIgnoreRemainingEvents()
        }

        // Verify API was only called ONCE
        coVerify(exactly = 1) { repository.getProducts() }
    }

    @Test
    fun `invoke should emit failure when repository fails`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { repository.getProducts() } returns Result.failure(exception)

        // When & Then
        useCase().test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
            awaitComplete()
        }
    }
}

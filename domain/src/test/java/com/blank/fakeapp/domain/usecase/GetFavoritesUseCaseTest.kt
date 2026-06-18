package com.blank.fakeapp.domain.usecase

import app.cash.turbine.test
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavoritesUseCaseTest {

    @Test
    fun `invoke should return favorites from repository`() = runTest {
        // Given
        val repository = mockk<ProductRepository>()
        val useCase = GetFavoritesUseCase(repository)
        val favorites = listOf(Product(1, "P1", 10.0, "D1", "C1", "I1", isFavorite = true))
        
        every { repository.getFavoriteProducts() } returns flowOf(favorites)

        // When & Then
        useCase().test {
            assertEquals(favorites, awaitItem())
            awaitComplete()
        }
    }
}

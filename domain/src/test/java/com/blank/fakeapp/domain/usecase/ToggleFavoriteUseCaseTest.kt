package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    @Test
    fun `invoke should call repository toggleFavorite`() = runTest {
        // Given
        val repository = mockk<ProductRepository>(relaxed = true)
        val useCase = ToggleFavoriteUseCase(repository)
        val product = Product(1, "P1", 10.0, "D1", "C1", "I1")

        // When
        useCase(product)

        // Then
        coVerify { repository.toggleFavorite(product) }
    }
}

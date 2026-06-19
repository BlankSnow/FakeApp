package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.local.dao.FavoriteDao
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.data.remote.dto.ProductDto
import com.blank.fakeapp.data.remote.dto.RatingDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var api: FakeStoreApi
    private lateinit var dao: FavoriteDao
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        dao = mockk()
        repository = ProductRepositoryImpl(api, dao)
    }

    @Test
    fun `getProducts returns success when API call is successful`() = runTest {
        // Given
        val remoteProducts = listOf(
            ProductDto(1, "Product 1", 10.0, "Desc 1", "Cat 1", "Img 1", RatingDto(4.0, 5))
        )
        coEvery { api.getProducts() } returns remoteProducts

        // When
        val result = repository.getProducts()

        // Then
        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertEquals(1, products?.size)
        assertEquals(false, products?.first()?.isFavorite)
    }

    @Test
    fun `getProducts returns failure when API call fails`() = runTest {
        // Given
        val exception = Exception("Network Error")
        coEvery { api.getProducts() } throws exception

        // When
        val result = repository.getProducts()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}

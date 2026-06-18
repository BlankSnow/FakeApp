package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.mapper.toDomain
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepositoryImpl(
    private val api: FakeStoreApi
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = api.getProducts()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        // TODO: Implementar con Room
        return flowOf(emptyList())
    }

    override suspend fun toggleFavorite(product: Product) {
        // TODO: Implementar con Room
    }
}

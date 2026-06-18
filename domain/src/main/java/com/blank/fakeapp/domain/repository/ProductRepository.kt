package com.blank.fakeapp.domain.repository

import com.blank.fakeapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    fun getFavoriteProducts(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product)
}

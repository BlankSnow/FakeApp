package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.local.dao.FavoriteDao
import com.blank.fakeapp.data.mapper.toDomain
import com.blank.fakeapp.data.mapper.toEntity
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val api: FakeStoreApi,
    private val favoriteDao: FavoriteDao
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val remoteProducts = api.getProducts()
            val products = remoteProducts.map { it.toDomain() }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return favoriteDao.getFavoriteProducts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(product: Product) = withContext(Dispatchers.IO) {
        if (product.isFavorite) {
            favoriteDao.deleteFavorite(product.toEntity())
        } else {
            favoriteDao.insertFavorite(product.toEntity())
        }
    }
}

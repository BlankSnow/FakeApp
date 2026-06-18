package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getFavoriteProducts()
    }
}

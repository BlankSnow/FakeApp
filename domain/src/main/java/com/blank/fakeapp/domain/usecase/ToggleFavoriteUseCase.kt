package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository

class ToggleFavoriteUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.toggleFavorite(product)
    }
}

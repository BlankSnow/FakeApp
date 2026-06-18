package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getProducts()
    }
}

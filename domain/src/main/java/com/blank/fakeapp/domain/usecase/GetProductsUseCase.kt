package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    /**
     * Obtiene los productos de red y los mantiene sincronizados 
     * con los favoritos de la base de datos local.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Result<List<Product>>> = flow {
        // Emitimos el resultado de la API
        emit(repository.getProducts())
    }.flatMapLatest { remoteResult ->
        remoteResult.fold(
            onSuccess = { remoteProducts ->
                // Si la API tuvo éxito, observamos los favoritos y los combinamos
                repository.getFavoriteProducts().map { favorites ->
                    Result.success(syncFavorites(remoteProducts, favorites))
                }
            },
            onFailure = { error ->
                // Si la API falló, emitimos el error
                flowOf(Result.failure(error))
            }
        )
    }

    private fun syncFavorites(remoteProducts: List<Product>, favorites: List<Product>): List<Product> {
        val favoriteIds = favorites.map { it.id }.toSet()
        return remoteProducts.map { product ->
            product.copy(isFavorite = favoriteIds.contains(product.id))
        }
    }
}

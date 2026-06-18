package com.blank.fakeapp.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _remoteProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ProductUiState> = combine(
        _remoteProducts,
        getFavoritesUseCase(),
        _isLoading,
        _error
    ) { remote, favorites, loading, error ->
        when {
            error != null -> ProductUiState.Error(error)
            loading && remote.isEmpty() -> ProductUiState.Loading
            else -> {
                val favoriteIds = favorites.map { it.id }.toSet()
                val updatedProducts = remote.map { product ->
                    product.copy(isFavorite = favoriteIds.contains(product.id))
                }
                ProductUiState.Success(updatedProducts)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductUiState.Loading
    )

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            getProductsUseCase().onSuccess { products ->
                _remoteProducts.value = products
            }.onFailure { error ->
                _error.value = error.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }
}

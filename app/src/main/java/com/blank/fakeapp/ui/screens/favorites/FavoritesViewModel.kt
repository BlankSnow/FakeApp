package com.blank.fakeapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = repository.getFavoriteProducts()
        .map { products ->
            FavoritesUiState.Success(products)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )

    fun removeFromFavorites(product: Product) {
        viewModelScope.launch {
            repository.toggleFavorite(product)
        }
    }
}

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val products: List<Product>) : FavoritesUiState
}

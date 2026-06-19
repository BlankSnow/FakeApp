package com.blank.fakeapp.ui.screens.favorites

import com.blank.fakeapp.domain.model.Product

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val products: List<Product>) : FavoritesUiState
    data class Error(val message: String) : FavoritesUiState
}

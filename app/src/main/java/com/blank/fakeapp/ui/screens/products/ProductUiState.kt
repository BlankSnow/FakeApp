package com.blank.fakeapp.ui.screens.products

import com.blank.fakeapp.domain.model.Product

sealed interface ProductUiState {
    object Loading : ProductUiState
    data class Success(val products: List<Product>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}
package com.blank.fakeapp.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            repository.getProducts().onSuccess { products ->
                _uiState.value = ProductUiState.Success(products)
            }.onFailure { error ->
                _uiState.value = ProductUiState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.toggleFavorite(product)
            val currentState = _uiState.value
            if (currentState is ProductUiState.Success) {
                val updatedList = currentState.products.map {
                    if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
                }
                _uiState.value = ProductUiState.Success(updatedList)
            }
        }
    }
}

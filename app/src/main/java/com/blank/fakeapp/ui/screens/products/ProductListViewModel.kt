package com.blank.fakeapp.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    // El estado de la UI ahora depende directamente del flujo del Caso de Uso
    val uiState: StateFlow<ProductUiState> = getProductsUseCase()
        .map { result ->
            result.fold(
                onSuccess = { products -> ProductUiState.Success(products) },
                onFailure = { error -> ProductUiState.Error(error.message ?: "Unknown error") }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductUiState.Loading
        )

    fun loadProducts() {
        // En este diseño reactivo, "loadProducts" se podría usar para forzar
        // un refresco si el flujo del UseCase permitiera re-ejecución manual,
        // pero por ahora el flujo se inicia solo al suscribirse (stateIn).
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }
}

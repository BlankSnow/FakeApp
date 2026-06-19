package com.blank.fakeapp.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.Product
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        loadProducts()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProductUiState> = refreshTrigger
        .flatMapLatest {
            getProductsUseCase()
                .map { result ->
                    result.fold(
                        onSuccess = { products -> ProductUiState.Success(products) },
                        onFailure = { error -> ProductUiState.Error(error.message ?: "Unknown error") }
                    )
                }
                .onStart { emit(ProductUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProductUiState.Loading
        )

    fun loadProducts() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }
}

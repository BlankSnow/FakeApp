package com.blank.fakeapp.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blank.fakeapp.R
import com.blank.fakeapp.ui.components.ProductItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is FavoritesUiState.Loading -> {
                CircularProgressIndicator()
            }
            is FavoritesUiState.Success -> {
                if (state.products.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_favorites_found),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(state.products, key = { it.id }) { product ->
                            ProductItem(
                                product = product,
                                onToggleFavorite = { viewModel.removeFromFavorites(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.blank.fakeapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.blank.fakeapp.R

sealed class Screen(
    val route: String,
    @get:StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Products : Screen("products", R.string.screen_products, Icons.Default.ShoppingCart)
    object Favorites : Screen("favorites", R.string.screen_favorites, Icons.Default.Favorite)
    object Profile : Screen("profile", R.string.screen_profile, Icons.Default.Person)
}

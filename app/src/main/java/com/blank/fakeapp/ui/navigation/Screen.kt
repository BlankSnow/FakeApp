package com.blank.fakeapp.ui.navigation

import androidx.annotation.StringRes
import com.blank.fakeapp.R

sealed class Screen(val route: String, @get:StringRes val titleRes: Int) {
    object Products : Screen("products", R.string.screen_products)
    object Favorites : Screen("favorites", R.string.screen_favorites)
    object Profile : Screen("profile", R.string.screen_profile)
}

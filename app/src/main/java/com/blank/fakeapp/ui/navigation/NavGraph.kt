package com.blank.fakeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.blank.fakeapp.ui.screens.favorites.FavoritesScreen
import com.blank.fakeapp.ui.screens.products.ProductListScreen
import com.blank.fakeapp.ui.screens.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Products.route,
        modifier = modifier
    ) {
        composable(Screen.Products.route) {
            ProductListScreen()
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}

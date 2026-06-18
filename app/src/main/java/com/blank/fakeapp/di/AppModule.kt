package com.blank.fakeapp.di

import com.blank.fakeapp.ui.screens.favorites.FavoritesViewModel
import com.blank.fakeapp.ui.screens.products.ProductListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ProductListViewModel)
    viewModelOf(::FavoritesViewModel)
}

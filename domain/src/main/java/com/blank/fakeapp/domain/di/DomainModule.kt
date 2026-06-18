package com.blank.fakeapp.domain.di

import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    // Usamos factoryOf porque los Use Cases suelen ser stateless 
    // y es mejor crear una instancia nueva cada vez (más limpio para testing)
    factoryOf(::GetProductsUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ToggleFavoriteUseCase)
}

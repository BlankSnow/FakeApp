package com.blank.fakeapp.domain.di

import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.GetProductsUseCase
import com.blank.fakeapp.domain.usecase.GetUserUseCase
import com.blank.fakeapp.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetProductsUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ToggleFavoriteUseCase)
    factoryOf(::GetUserUseCase)
}

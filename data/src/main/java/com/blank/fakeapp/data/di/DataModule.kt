package com.blank.fakeapp.data.di

import android.content.Context
import androidx.room.Room
import com.blank.fakeapp.data.local.db.FakeAppDatabase
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.data.repository.ProductRepositoryImpl
import com.blank.fakeapp.data.repository.UserRepositoryImpl
import com.blank.fakeapp.domain.repository.ProductRepository
import com.blank.fakeapp.domain.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    
    // Networking
    singleOf(::provideHttpLoggingInterceptor)
    singleOf(::provideOkHttpClient)
    singleOf(::provideRetrofit)
    singleOf(::provideFakeStoreApi)

    // Local Persistence (Room)
    single { provideDatabase(androidContext()) }
    single { get<FakeAppDatabase>().favoriteDao() }

    // Repositories
    singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(FakeStoreApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun provideFakeStoreApi(retrofit: Retrofit): FakeStoreApi {
    return retrofit.create(FakeStoreApi::class.java)
}

private fun provideDatabase(context: Context): FakeAppDatabase {
    return Room.databaseBuilder(
        context,
        FakeAppDatabase::class.java,
        "fake_app_db"
    ).build()
}

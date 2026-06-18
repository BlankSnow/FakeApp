package com.blank.fakeapp.di

import android.content.Context
import com.blank.fakeapp.data.di.dataModule
import com.blank.fakeapp.domain.di.domainModule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KoinModuleTest : KoinTest {

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `check Koin modules`() {
        checkModules {
            androidContext(mockk<Context>(relaxed = true))
            modules(appModule, dataModule, domainModule)
        }
    }
}

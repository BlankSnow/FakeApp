package com.blank.fakeapp.di

import android.content.Context
import com.blank.fakeapp.data.di.dataModule
import io.mockk.mockk
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.junit.Test

class KoinModuleTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `check Koin modules`() {
        checkModules {
            androidContext(mockk<Context>(relaxed = true))
            modules(appModule, dataModule)
        }
    }
}

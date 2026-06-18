package com.blank.fakeapp

import android.app.Application
import com.blank.fakeapp.di.appModule
import com.blank.fakeapp.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FakeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FakeApp)
            modules(appModule, dataModule)
        }
    }
}

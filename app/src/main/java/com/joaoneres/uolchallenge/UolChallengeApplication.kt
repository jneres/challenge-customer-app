package com.joaoneres.uolchallenge

import android.app.Application
import com.joaoneres.uolchallenge.core.network.networkModule
import com.joaoneres.uolchallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UolChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@UolChallengeApplication)
            modules(
                networkModule,
                appModule
            )
        }
    }
}
package com.yuzu.themoviedb

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.yuzu.themoviedb.injection.component.AppComponent
import com.yuzu.themoviedb.injection.component.DaggerAppComponent
import com.yuzu.themoviedb.injection.module.AppModule

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class TheMovieDBApplication: Application() {
    lateinit var component: AppComponent

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: TheMovieDBApplication private set
    }

    operator fun get(context: Context): TheMovieDBApplication {
        return context.applicationContext as TheMovieDBApplication
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()
        instance = this
        // DI
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }
}
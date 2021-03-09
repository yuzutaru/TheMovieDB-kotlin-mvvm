package com.yuzu.themoviedb.injection.component

import android.app.Application
import com.yuzu.themoviedb.injection.module.AppModule
import com.yuzu.themoviedb.model.api.MovieAPI
import com.yuzu.themoviedb.model.repository.MovieRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)

    //Movie API
    fun movieAPI(): MovieAPI
    fun movieRepository(): MovieRepository
}
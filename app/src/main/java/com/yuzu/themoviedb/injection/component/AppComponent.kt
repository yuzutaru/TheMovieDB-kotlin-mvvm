package com.yuzu.themoviedb.injection.component

import android.app.Application
import com.yuzu.themoviedb.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
}
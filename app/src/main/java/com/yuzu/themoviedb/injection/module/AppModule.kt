package com.yuzu.themoviedb.injection.module

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

@Module
class AppModule(private val app: Application) {
    @Provides
    fun app(): Application {
        return app
    }
}
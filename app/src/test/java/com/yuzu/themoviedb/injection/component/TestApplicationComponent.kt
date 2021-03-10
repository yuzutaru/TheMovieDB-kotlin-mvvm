package com.yuzu.themoviedb.injection.component

import com.yuzu.themoviedb.injection.AppRepositoryInjectTest
import com.yuzu.themoviedb.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface TestApplicationComponent {
    fun into(test: AppRepositoryInjectTest)
}
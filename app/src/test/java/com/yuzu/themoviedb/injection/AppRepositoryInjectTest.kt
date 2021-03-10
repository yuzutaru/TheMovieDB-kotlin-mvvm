package com.yuzu.themoviedb.injection

import com.yuzu.themoviedb.TheMovieDBApplication
import com.yuzu.themoviedb.injection.component.DaggerTestApplicationComponent
import com.yuzu.themoviedb.injection.module.TestApplicationModule
import com.yuzu.themoviedb.model.api.MovieAPI
import com.yuzu.themoviedb.model.data.Movie
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.utils.API_KEY
import io.mockk.every
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class AppRepositoryInjectTest {
    @Inject
    lateinit var api: MovieAPI

    @Inject
    lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        val component = DaggerTestApplicationComponent.builder()
            .appModule(TestApplicationModule(TheMovieDBApplication()))
            .build()
        component.into(this)
    }

    @Test
    fun apiPopularTest() {
        Assert.assertNotNull(api)
        every { api.popular(API_KEY) } returns Single.just(Movie())
        val result = api.popular(API_KEY)
        result.test().assertValue(Movie())
    }

    @Test
    fun apiTopRatedTest() {
        Assert.assertNotNull(api)
        every { api.topRated(API_KEY) } returns Single.just(Movie())
        val result = api.topRated(API_KEY)
        result.test().assertValue(Movie())
    }

    @Test
    fun apiNowPlayingTest() {
        Assert.assertNotNull(api)
        every { api.nowPlaying(API_KEY) } returns Single.just(Movie())
        val result = api.nowPlaying(API_KEY)
        result.test().assertValue(Movie())
    }
}
package com.yuzu.themoviedb.model.local

import com.yuzu.themoviedb.model.data.MovieData
import org.junit.Assert
import org.junit.Test

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

class MovieDAOTest: MovieDBTest() {
    @Test
    fun getAllTest() {
        db.movieDAO().insert(listOf(MovieData(0), MovieData(1)))
        db.movieDAO().insert(MovieData(2))
        val data = db.movieDAO().getAll().blockingGet()
        Assert.assertEquals(data.size, 3)
    }

    @Test
    fun getDataById() {
        db.movieDAO().insert(listOf(MovieData(0), MovieData(1)))
        val data = db.movieDAO().getDataById(0).blockingGet()
        Assert.assertNotNull(data)
    }

    @Test
    fun deleteTest() {
        db.movieDAO().insert(listOf(MovieData(0), MovieData(1)))
        val data = db.movieDAO().getAll().blockingGet()
        Assert.assertEquals(data.size, 2)

        db.movieDAO().delete(MovieData(0))
        val dataDeleted = db.movieDAO().getAll().blockingGet()
        Assert.assertEquals(dataDeleted.size, 1)
    }
}
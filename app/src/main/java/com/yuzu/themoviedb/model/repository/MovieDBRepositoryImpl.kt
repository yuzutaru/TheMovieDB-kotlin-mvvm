package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.local.MovieDAO
import io.reactivex.Single
import java.util.concurrent.Executor

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

class MovieDBRepositoryImpl(private val dao: MovieDAO, private val exec: Executor): MovieDBRepository {
    override fun getAll(): Single<List<MovieData>> {
        return dao.getAll()
    }

    override fun insert(data: MovieData) {
        exec.execute {
            dao.insert(data)
        }
    }

    override fun insert(data: List<MovieData>) {
        exec.execute {
            dao.insert(data)
        }
    }

    override fun delete(data: MovieData) {
        exec.execute {
            dao.delete(data)
        }
    }
}
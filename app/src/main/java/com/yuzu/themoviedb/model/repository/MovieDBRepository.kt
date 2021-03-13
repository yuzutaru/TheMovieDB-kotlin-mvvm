package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.data.MovieData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

interface MovieDBRepository {
    fun getAll(): Single<List<MovieData>>
    fun insert(data: MovieData)
    fun insert(data: List<MovieData>)
    fun delete(data: MovieData)
}
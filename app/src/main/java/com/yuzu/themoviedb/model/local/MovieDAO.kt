package com.yuzu.themoviedb.model.local

import androidx.room.*
import com.yuzu.themoviedb.model.data.MovieData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

@Dao
interface MovieDAO {
    @Query("SELECT * from MovieData")
    fun getAll(): Single<List<MovieData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<MovieData>)

    @Delete
    fun delete(data: MovieData)
}
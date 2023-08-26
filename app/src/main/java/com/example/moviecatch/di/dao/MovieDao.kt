package com.example.moviecatch.di.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Insert
    fun  addFavoriteMovie(movie:MovieData)
    @Insert
    fun  addWatchlistMovie(movie:MovieData)
    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    suspend fun readAllFavorites():List<MovieData>
    @Query("SELECT * FROM movies WHERE isInWatchlist = 1")
    fun readAllWatchlist():List<MovieData>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): MovieData?

    @Update
    fun updateMovie(movie: MovieData)
    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovie(movieId: Int)
}